package br.com.lemos.lemosfood.infrastructure.service.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.lemos.lemosfood.core.storage.StorageProperties;
import br.com.lemos.lemosfood.domain.service.FotoStorageService;

@Service
public class S3FotoStorageService implements FotoStorageService {
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(novaFoto.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					caminhoArquivo,
					novaFoto.getInputStream(),
					objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		}catch (Exception e) {
			throw new StorageException("Nao foi possível enviar arquivo para a Amazon S3", e);
		}
	}

	@Override
	public void remover(String nomeArquivo) {
		
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		return null;
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}
}