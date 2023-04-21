package com.trybe.acc.java.minhasseries.service;

import com.trybe.acc.java.minhasseries.exception.EpisodioExistenteException;
import com.trybe.acc.java.minhasseries.exception.SerieExistenteException;
import com.trybe.acc.java.minhasseries.exception.SerieNaoEncontradaException;
import com.trybe.acc.java.minhasseries.model.Episodio;
import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.repository.SerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe service das series.
 */
@Service
public class SerieService {
  @Autowired
  private SerieRepository serieRepository;

  /**
   * Cria um novo registro de uma serie.
   */
  public Serie create(String nome) throws SerieExistenteException {
    if (serieRepository.existsByNome(nome)) {
      throw new SerieExistenteException("Série Existente");
    }
    Serie serie = new Serie(nome);
    Serie serieCriada = serieRepository.save(serie);
    return serieCriada;
  }

  /**
   * Busca uma serie pelo seu id.
   */
  public Serie getById(Long id) throws SerieNaoEncontradaException {
    Serie serie = serieRepository.findById(id).orElse(null);
    System.out.println(serie);
    if (serie == null) {
      throw new SerieNaoEncontradaException("Série não encontrada");
    }
    return serie;
  }

  /**
   * Retorna uma lista de todas series registradas.
   */
  public List<Serie> getAll() {
    return serieRepository.findAll();
  }

  /**
   * Retorna uma lista de todos episodios de uma serie.
   */
  public List<Episodio> getEpisodesFromSerie(Long id) {
    Serie serie = this.getById(id);
    return serie.getEpisodios();
  }

  /**
   * Deleta o registro de uma serie.
   */
  public void delete(Long id) {
    Serie serie = this.getById(id);
    serieRepository.delete(serie);
  }

  /**
   * Cria um novo registro de um episodio para uma serie.
   */
  @CircuitBreaker(name = "episodios")
  public Serie createEpisode(Long id, int numero, int duracaoEmMinutos) {
    System.out.println("PASSOOOUU");
    Serie serie = this.getById(id);
    Episodio episodio = new Episodio(numero, duracaoEmMinutos);
    if (serie.getEpisodios().contains(episodio)) {
      throw new EpisodioExistenteException("Episódio Existente");
    }
    serie.adicionarEpisodio(episodio);
    return serieRepository.save(serie);
  }

  /**
   * Retorna o tempo gasto vendo as series.
   */
  public Map<String, Integer> timeSpent() {
    List<Serie> series = serieRepository.findAll();
    int somaTempo = series.stream().map(Serie::getEpisodios).flatMap(List::stream)
        .map(Episodio::getDuracaoEmMinutos).reduce(0, (acc, duracao) -> acc + duracao);
    return Map.of("tempoEmMinutos", somaTempo);
  }
}
