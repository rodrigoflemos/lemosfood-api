package br.com.lemos.lemosfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lemos.lemosfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.lemos.lemosfood.domain.model.Cidade;
import br.com.lemos.lemosfood.domain.model.Estado;
import br.com.lemos.lemosfood.domain.repository.CidadeRepository;
import br.com.lemos.lemosfood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com código %d", estadoId)));
		
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
//	public void excluir(Long restauranteId) {
//		try {
//			restauranteRepository.remover(restauranteId);
//		}catch (EmptyResultDataAccessException e) {
//			throw new EntidadeNaoEncontradaException(
//				String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
//		}catch (DataIntegrityViolationException e) {
//			throw new EntidadeEmUsoException(
//				String.format("Restaurante de código %d não pode ser removido, pois está em uso", restauranteId));
//		}
//	}
}