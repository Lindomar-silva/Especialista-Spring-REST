package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	public final static String MSG_ERRO_GENERICO_USUARIO_FINAL = "Ocorreu um erro interno inesperado de sistema. "
			+ "Tente novamente e se o erro persistir, entre em contato como o administrador do sistema";
	
	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String datail = String.format("O método '%s' não é suportado pelo recurso que tentou acessar. "
				+ "Informe um recurso válido!", ex.getMethod());
		
		Problem problem = getProblem(status, ProblemType.METODO_NAO_PERMTIDO, datail);
		problem.setUserMsg(MSG_ERRO_GENERICO_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}

	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String datail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente!";
		
		List<Problem.Object> problemFields = bindingResult.getAllErrors().stream()
				.map(error -> {
					String name = error.getObjectName();
					String msgError = messageSource.getMessage(error, LocaleContextHolder.getLocale());
					
					if (error instanceof FieldError) {
						name = ((FieldError) error).getField();
					}
					
					return new Problem.Object(name, msgError);
					})
				.collect(Collectors.toList());
		
		Problem problem = getProblem(status, ProblemType.DADOS_INVALIDOS, datail);
		problem.setUserMsg(datail);
		problem.setObjects(problemFields);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleErroSistema(Exception ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		String datail = MSG_ERRO_GENERICO_USUARIO_FINAL;
		Problem problem = getProblem(status, ProblemType.ERRO_DE_SISTEMA, datail);

//		ex.printStackTrace();
		log.error(ex.getMessage(), ex);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String datail = String.format("O recurso '%s', não foi encontrado. Informe um recurso válido!",
				ex.getRequestURL());

		Problem problem = getProblem(status, ProblemType.RECURSO_NAO_ENCONTRADO, datail);
		problem.setUserMsg(MSG_ERRO_GENERICO_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String datail = String.format(
				"O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. "
						+ "Informe um valor compátivel com o tipo %s!",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = getProblem(status, ProblemType.PARAMETRO_INVALIDO, datail);
		problem.setUserMsg(MSG_ERRO_GENERICO_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
			
		} else if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}
		
		String datail = "O corpo da requisição está inválido. Verifique erro de sintaxe!";

		Problem problem = getProblem(status, ProblemType.REQUISICAO_INVALIDA, datail);
		problem.setUserMsg(MSG_ERRO_GENERICO_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String datail = String.format("A propriedade '%s' não existe. Verifique erro de sintaxe!", 
				ex.getPropertyName());
		
		Problem problem = getProblem(status, ProblemType.REQUISICAO_INVALIDA, datail);
		problem.setUserMsg(MSG_ERRO_GENERICO_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		String datail = String.format(
				"A propriedade '%s' recebeu o valor '%s', que é um tipo inválido. "
				+ "Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = getProblem(status, ProblemType.REQUISICAO_INVALIDA, datail);
		problem.setUserMsg(MSG_ERRO_GENERICO_USUARIO_FINAL);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;

		Problem problem = getProblem(status, problemType, ex.getMessage());

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		Problem problem = getProblem(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<?> handleMaxUploadFileSizeExceeded(MaxUploadSizeExceededException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.PAYLOAD_TOO_LARGE;
		String detail = ex.getMessage();

		if (ex.getRootCause() instanceof FileSizeLimitExceededException) {
			var fileExce = (FileSizeLimitExceededException) ex.getRootCause();
			detail = String.format(
					"O arquivo que está tentando fazer upload, execede o tamanho máximo permitido de %d bytes",
					fileExce.getPermittedSize());
		}

		Problem problem = getProblem(status, ProblemType.TAMANHO_EXCEDIDO, detail);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problem problem = getProblem(status, ProblemType.NEGOCIO_EXCEPTION, ex.getMessage());
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}


	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			Problem problem = new Problem();
			problem.setStatus(status.value());
			problem.setTitle(status.getReasonPhrase());
			problem.setTimestamp(OffsetDateTime.now());
			problem.setUserMsg(MSG_ERRO_GENERICO_USUARIO_FINAL);
			body = problem;
			
		} else if (body instanceof String) {
			Problem problem = new Problem();
			problem.setStatus(status.value());
			problem.setTitle((String) body);
			problem.setTimestamp(OffsetDateTime.now());
			problem.setUserMsg(MSG_ERRO_GENERICO_USUARIO_FINAL);
			body = problem;
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem getProblem(HttpStatus status, ProblemType problemType, String datail) {
		Problem problem = new Problem();
		problem.setStatus(status.value());
		problem.setType(problemType.getUri());
		problem.setTitle(problemType.getTitle());
		problem.setDetail(datail);
		problem.setUserMsg(datail);
		problem.setTimestamp(OffsetDateTime.now());

		return problem;
	}
}
