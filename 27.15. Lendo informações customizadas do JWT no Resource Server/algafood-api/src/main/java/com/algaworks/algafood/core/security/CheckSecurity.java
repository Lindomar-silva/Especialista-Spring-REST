package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

	public @interface Cozinhas {

		@PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {
		}
	}

	public @interface Restaurantes {

		@PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {
		}

		@PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurantes()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarCadastro {
		}

		@PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarFuncionamento {
		}
	}

	public @interface Pedidos {

		@PreAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodePesquisar {
		}

		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
				+ "@algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or"
				+ "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeBuscar {
		}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeCriar {
		}

		@PreAuthorize("@algaSecurity.podeGerenciarPedido(#pedidoUuId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarPedidos {
		}
	}
	
	public @interface FormasPagamento {

		@PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {
		}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {
		}
	}
	
	public @interface Cidades {

		@PreAuthorize("@algaSecurity.podeConsultarCidades()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {
		}
	}
	
	public @interface Estados {

		@PreAuthorize("@algaSecurity.podeConsultarEstados()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {
		}
	}
	
	public @interface UsuariosGruposPermissoes {

		@PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {
		}
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')) or "
				+ "@algaSecurity.getUsuarioId() == #usuarioId")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultarUsuario {
		}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and @algaSecurity.usuarioAutenticadoIgual(#usuarioId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeAlterarPropriaSenha {
		}

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
				+ "@algaSecurity.usuarioAutenticadoIgual(#usuarioId))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeAlterarUsuario {
		}

		@PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {
		}
	}
	
	public @interface Estatisticas {

		@PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {
		}
	}
}
