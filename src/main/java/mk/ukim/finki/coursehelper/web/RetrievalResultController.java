package mk.ukim.finki.coursehelper.web;


import mk.ukim.finki.coursehelper.dto.RetrievalResultDTO;
import mk.ukim.finki.coursehelper.model.DocumentChunk;
import mk.ukim.finki.coursehelper.model.Query;
import mk.ukim.finki.coursehelper.model.RetrievalResult;
import mk.ukim.finki.coursehelper.service.DocumentChunkService;
import mk.ukim.finki.coursehelper.service.QueryService;
import mk.ukim.finki.coursehelper.service.RetrievalResultService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/results")
public class RetrievalResultController {
    private final RetrievalResultService resultService;
    private final QueryService queryService;
    private final DocumentChunkService chunkService;

    public RetrievalResultController(RetrievalResultService resultService,
                                     QueryService queryService,
                                     DocumentChunkService chunkService) {
        this.resultService = resultService;
        this.queryService = queryService;
        this.chunkService = chunkService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetrievalResultDTO create(@RequestBody RetrievalResultDTO dto) {
        Query q = queryService.getQueryById(dto.query().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Query not found"));
        DocumentChunk c = chunkService.getDocumentChunkById(dto.documentChunk().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chunk not found"));
        RetrievalResult r = new RetrievalResult();
        r.setQuery(q);
        r.setDocumentChunk(c);
        r.setScore(dto.score());
        RetrievalResult saved = resultService.saveResult(r);
        return new RetrievalResultDTO(
                saved.getId(),
                saved.getQuery(),
                saved.getDocumentChunk(),
                saved.getScore()
        );
    }

    @GetMapping
    public List<RetrievalResultDTO> listAll() {
        return resultService.getAllResults().stream()
                .map(r -> new RetrievalResultDTO(
                        r.getId(),
                        r.getQuery(),
                        r.getDocumentChunk(),
                        r.getScore()
                ))
                .collect(Collectors.toList());
    }
}

