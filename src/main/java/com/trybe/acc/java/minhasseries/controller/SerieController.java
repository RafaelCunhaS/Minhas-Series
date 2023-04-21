package com.trybe.acc.java.minhasseries.controller;

import com.trybe.acc.java.minhasseries.model.Episodio;
import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.service.SerieService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe Controller que recebe e envia respostas ao cliente.
 */
@RestController
@RequestMapping(value = "/series")
public class SerieController {
  @Autowired
  private SerieService serieService;

  /**
   * Lista todas series salvos.
   */
  @GetMapping("")
  public ResponseEntity<List<Serie>> getAll() {
    return ResponseEntity.ok(serieService.getAll());
  }

  /**
   * Insere uma nova serie.
   */
  @PostMapping("")
  public ResponseEntity<Serie> create(@RequestBody Serie serie) {
    return ResponseEntity.ok(serieService.create(serie.getNome()));
  }


  /**
   * Deleta uma serie específica.
   */
  @DeleteMapping("/{serieId}")
  @ResponseStatus(value = HttpStatus.OK)
  public void delete(@PathVariable Long serieId) {
    serieService.delete(serieId);
  }

  /**
   * Insere um novo episodio na serie.
   */
  @PostMapping("/{serieId}/episodios")
  public ResponseEntity<Serie> createEpisode(@PathVariable Long serieId,
      @RequestBody Episodio episodio) {
    System.out.println(episodio);
    return ResponseEntity.ok(
        serieService.createEpisode(serieId, episodio.getNumero(), episodio.getDuracaoEmMinutos()));
  }

  /**
   * Retorna episodios de uma determinada serie.
   */
  @GetMapping("/{serieId}/episodios")
  public ResponseEntity<List<Episodio>> getEpisodesFromSerie(@PathVariable Long serieId) {
    return ResponseEntity.ok(serieService.getEpisodesFromSerie(serieId));
  }

  /**
   * Retorna o tempo gasto vendo as series.
   */
  @GetMapping("/tempo")
  public ResponseEntity<Map<String, Integer>> timeSpent() {
    return ResponseEntity.ok(serieService.timeSpent());
  }
}

