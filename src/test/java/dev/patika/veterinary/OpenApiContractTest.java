package dev.patika.veterinary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OpenApiContractTest {

    private static final Path CHECKED_CONTRACT = Path.of("docs/openapi.json");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void generatedContractMatchesTheInspectableArtifact() throws Exception {
        String response = mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode generated = objectMapper.readTree(response);
        assertThat(generated.path("openapi").asText()).startsWith("3.");
        assertThat(generated.path("info").path("title").asText()).isEqualTo("Veterinary Management API");
        assertThat(generated.path("paths").has("/api/appointments")).isTrue();
        assertThat(generated.path("paths").has("/api/vaccinations/expiring")).isTrue();
        assertThat(generated.path("paths").has("/api/owners/{id}/animals")).isTrue();

        if (Boolean.getBoolean("openapi.update")) {
            Files.createDirectories(CHECKED_CONTRACT.getParent());
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(CHECKED_CONTRACT.toFile(), generated);
        }

        JsonNode checked = objectMapper.readTree(CHECKED_CONTRACT.toFile());
        assertThat(generated)
                .as("docs/openapi.json must match the runtime contract; regenerate with -Dopenapi.update=true")
                .isEqualTo(checked);
    }

    @Test
    void swaggerUiHasAStableEntryPoint() throws Exception {
        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/swagger-ui/index.html"));
    }
}
