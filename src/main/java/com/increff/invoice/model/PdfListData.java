package com.increff.invoice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfListData {

	private Integer sno;
	private Integer quantity;
	private String barcode;
	private String product;
	private Double unitPrice;

	private Double amount;
}
