package com.vertical.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@Table(name = "product", schema = "public")
public class Product {

	@Id
	private Long id;

	@OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST })
	private List<OrderRegistration> orderRegistrations;

	public Product(Long id) {
		this.id = id;
	}
}
