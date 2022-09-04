package com.project.kcookserver.store.enums;

import lombok.Getter;

@Getter
public enum Area {
	SEOUL("서울특별시"),
	GYEONGGI("경기도"),
	BUSAN("부산광역시"),
	DAEGU("대구광역시"),
	INCHEON("인천광역시"),
	GWANGJU("광주광역시"),
	DAEJEON("대전광역시"),
	ULSAN("울산광역시"),
	SEJONG("세종특별자치시"),
	KANGWONDO("강원도"),
	CHUNGCHEONGBUKDO("충청북도"),
	CHUNGCHEONGNAMDO("충청남도"),
	JEONLABUKDO("전라북도"),
	JEONLANAMDO("전라남도"),
	GYEONGSANGBUKDO("경상북도"),
	GYEONGSANGNAMDO("경상남도"),
	JEJU("제주도");

	private String koreanWord;

	Area(String koreanWord) {
		this.koreanWord = koreanWord;
	}
}