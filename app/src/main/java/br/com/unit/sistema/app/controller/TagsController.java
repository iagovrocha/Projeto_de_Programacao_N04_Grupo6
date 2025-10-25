package br.com.unit.sistema.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import br.com.unit.sistema.app.controller.dto.TagsDTO;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @PostMapping
    public ResponseEntity<TagsDTO> createTag(@RequestBody TagsDTO tag) {
        System.out.println(tag.toString());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{idTag}")
    public ResponseEntity<TagsDTO> getTag(@PathVariable long idTag) {
        TagsDTO dummy = new TagsDTO("demo-tag-" + idTag, "#FFFFFF");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


}
