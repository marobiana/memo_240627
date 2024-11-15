package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;
import com.memo.interceptor.PermissionInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration // 설정 관련 spring bean
public class WebMvcConfig implements WebMvcConfigurer {
	
	private final PermissionInterceptor interceptor;
	
	// 인터셉터 설정
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(interceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/css/**", "/img/**", "/images/**", "/user/sign-out");
	}
	
	// 예언된 이미지 path와 서버에 업로드 된 실제 이미지와 매핑
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // path  http://localhost/images/aaaa_1730889238618/key-chain-2590442_640.jpg
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 파일 위치
	}
}
