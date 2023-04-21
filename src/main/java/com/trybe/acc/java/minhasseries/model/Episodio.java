package com.trybe.acc.java.minhasseries.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entidade de Episodio.
 */
@Entity
public class Episodio {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private int numero;

  @Column(name = "duracao_em_minutos")
  private int duracaoEmMinutos;

  @JoinColumn(name = "serie_id")
  @ManyToOne
  private Serie serie;

  public Episodio() {}

  /**
   * Método construtor.
   */
  public Episodio(int numero, int duracaoEmMinutos) {
    this.numero = numero;
    this.duracaoEmMinutos = duracaoEmMinutos;
  }

  /**
   * Método construtor.
   */
  public Episodio(Long id, int numero, int duracaoEmMinutos, Serie serie) {
    this.numero = numero;
    this.duracaoEmMinutos = duracaoEmMinutos;
    this.serie = serie;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public int getDuracaoEmMinutos() {
    return duracaoEmMinutos;
  }

  public void setDuracaoEmMinutos(int duracaoEmMinutos) {
    this.duracaoEmMinutos = duracaoEmMinutos;
  }
}
