package org.dismefront.api.importHistory;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.importHistory.ImportHistoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/import-history")
public class ImportHistoryController {

    private final ImportHistoryRepository importHistoryRepository;

    @GetMapping("/get-all")
    public ResponseEntity getAllImportHistory() {
        return ResponseEntity.ok().body(importHistoryRepository.findAll());
    }

}
