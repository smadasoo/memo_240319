package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

// 파일을 업로드 해주는 클래스
@Component
public class FileManagerService {

	// 실제 업로드가 된 이미지가 저장될 서버의 경로
	public static final String FILE_UPLOAD_PATH = "D:\\Images/";
	
	
	// 메소드를 만들땐 input output을 항상 생각하자
	// input : MultipartFile, userLoginId
	// output : String(이미지 경로)
	public String uploadFile(MultipartFile file, String loginId) {
		// 폴더(디렉토리) 생성
		// ex) aaaa_461515613/sun.png
		String directoryName = loginId + "_" + System.currentTimeMillis();
		
//		D:\잊우현\6_spring_project\memo\memo_workspace\Images/aaaaaaa_4651665131
		String filePath = FILE_UPLOAD_PATH + directoryName + "/";
		
		// 폴더 생성
		File directroy = new File(filePath);
		if (directroy.mkdir() == false) {
			// 폴더 생서 시 샐패하면 경로를 null로 리턴
			return null;
		}
		
		// 파일 업로드 
		try {
			byte[] bytes = file.getBytes();
			//★★★★★ 한글 이름으로 된 이미지는 업로드 불가하므로 나중에 영문자로 바꾸기기기기기긱
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 이미지 업로드 실패시 경로 null
		}
		
		// 파일 업로드가 성공하믄 이미지 url path를 리턴
		// 주소는 이렇게 될 것 이다.
		// /images/aaaa_461515613/sun.png
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
}
