package com.bezkoder.springjwt.payload.request;

import lombok.Data;

@Data
public class PostUpdateRequest {
	String title;
	String text;
}
