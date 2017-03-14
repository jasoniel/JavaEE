package br.com.casadocodigo.loja.infra;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@RequestScoped
public class FileSaver {
	
	@Inject
	private HttpServletRequest request;
	
	private static final String CONTENT_DISPOSITION = "content-disposition";
	
	private static final String FILENAME_KEY ="filename=";

	public String writer(String baseFolder, Part multipartFile) {
		// TODO Auto-generated method stub
		
		String serverPath = request.getServletContext().getRealPath("/"+baseFolder);
		
		String fileName = extractFileName(multipartFile.getHeader(CONTENT_DISPOSITION));
		
		String path = serverPath + "/" +fileName;
		
		try {
			multipartFile.write(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return baseFolder+"/"+fileName;
	}
	
	private String extractFileName(String contentDisposition) {
		// TODO Auto-generated method stub
		if(contentDisposition == null)
			return null;
	
		String fileNameKey = "filename=";
		int startIndex = contentDisposition.indexOf(fileNameKey);
		
		if( startIndex == -1){
			return null;
		}
		
		String  fileName = contentDisposition.substring(startIndex,fileNameKey.length());
		
		if(fileName.startsWith("\"")){
			int endIndex = fileName.indexOf("\"",1);
			if(endIndex != -1){
				return fileName.substring(1, endIndex);
			}
		}else{
			int endIndex = fileName.indexOf(";");
			if(endIndex != -1){
				return fileName.substring(0,endIndex);
			}
		}
		return fileName;
	}
	
	

}
