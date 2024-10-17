package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

	public RestauranteDTOAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;   
	
	@Override
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		mapper.map(restaurante, restauranteModel);

		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		}
		
		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
		}
		
		if (algaSecurity.podeConsultarCidades()) {
			if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
				restauranteModel.getEndereco().getCidade()
						.add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
			}
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			if (restaurante.ativacaoPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
			}
	
			if (restaurante.inativacaoPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
			}
		}
		
		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
			if (restaurante.aberturaPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
			}
	
			if (restaurante.fechamentoPermitido()) {
				restauranteModel.add(algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
			}
		}
		
		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteDTO> collectionModel = super.toCollectionModel(entities);

		if (algaSecurity.podeConsultarRestaurantes()) {
			collectionModel.add(algaLinks.linkToRestaurantes());
		}

		return collectionModel;
	}

}
