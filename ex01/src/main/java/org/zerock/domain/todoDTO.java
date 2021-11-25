package org.zerock.domain;

import java.util.Date;

import lombok.Data;
//@Data는 get set 이 자동으로 생성된다
@Data

public class todoDTO {

	private String title;
	private Date dueDate;
}
