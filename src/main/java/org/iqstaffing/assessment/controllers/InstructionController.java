package org.iqstaffing.assessment.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.iqstaffing.assessment.models.Instruction;
import org.iqstaffing.assessment.services.InstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/instructions")
@Tag(name = "Recipes instruction")
public class InstructionController {

    private final InstructionService instructionService;

    @Autowired
    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @Operation(description = "Create recipe instruction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "201", description = "Instruction was successful created")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Instruction> add(@RequestBody Instruction instruction) {
        Instruction created = instructionService.add(instruction);
        log.info("Instruction successful created.");
        return new ResponseEntity(created, HttpStatus.CREATED);
    }

    @Operation(description = "Patch recipe instruction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "404", description = "Instruction not found in database"),
            @ApiResponse(responseCode = "200", description = "Request was successful")})
    @PatchMapping("/{instructionId}")
    public ResponseEntity<Instruction> fetch(@PathVariable(value = "instructionId") Long instructionId, @RequestBody Instruction instruction) {
        instructionService.update(instructionId, instruction.getInstruction());
        log.info("Instruction with {} was successful updated.", instructionId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Get instruction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "404", description = "Instruction not found in database"),
            @ApiResponse(responseCode = "200", description = "Request was successful")})
    @GetMapping(value = "/{instructionId}")
    public ResponseEntity<Instruction> get(@PathVariable(value = "instructionId") Long instructionId) {
        Instruction instruction = instructionService.getById(instructionId);
        return new ResponseEntity(instruction, HttpStatus.OK);
    }

    @Operation(description = "Get all instructions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "200", description = "Request was successful")})
    @GetMapping()
    public ResponseEntity<List<Instruction>> getAll() {
        List<Instruction> instructions = instructionService.getAll();
        log.info("Fetch all instructions.");
        return new ResponseEntity(instructions, HttpStatus.OK);
    }

    @Operation(description = "Delete instruction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "404", description = "Instruction not found in database"),
            @ApiResponse(responseCode = "204", description = "Instruction deleted successfully")})
    @DeleteMapping(value = "/{instructionId}")
    public ResponseEntity<?> delete(@PathVariable(value = "instructionId") Long instructionId) {
        instructionService.delete(instructionId);
        log.info("Deleted instruction with id={}", instructionId);
        return ResponseEntity.noContent().build();
    }
}
