package com.upaio.messagebroker.chassis.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
@Slf4j
public class OpenApiConfig {
  @Value("${api.title}")
  private String apiTitle;

  @Value("${api.version}")
  private String apiVersion;

  @Value("${api.description}")
  private String apiDescription;

  /**
   * Customer global header open api customiser.
   *
   * @return the open api customiser
   */
  @Bean
  public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

    log.info("Cargando SwaggerConfig - customerGlobalHeaderOpenApiCustomiser");

    return openApi -> openApi
        .info(new Info().title(apiTitle).description(apiDescription).version(apiVersion)).getPaths()
        .values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
          ApiResponses apiResponses = operation.getResponses();
          ApiResponse apiResponseOk =
              new ApiResponse().description("Peticion Exitosa").content(new Content().addMediaType(
                  org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType()));
          ApiResponse apiResponseBadRequest = new ApiResponse().description("Peticion incorrecta")
              .content(new Content().addMediaType(
                  org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType()));
          ApiResponse apiResponseUnauthorized =
              new ApiResponse().description("No esta autorizado para este recurso")
                  .content(new Content().addMediaType(
                      org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType()));
          ApiResponse apiResponseForbidden = new ApiResponse()
              .description("El recurso no es accesible").content(new Content().addMediaType(
                  org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType()));
          ApiResponse apiResponseNotFound = new ApiResponse()
              .description("No se ha encontrado el recurso").content(new Content().addMediaType(
                  org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType()));
          ApiResponse apiResponseInternalServerError = new ApiResponse()
              .description("Error Interno No Controlado").content(new Content().addMediaType(
                  org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType()));
          ApiResponse apiResponseServiceUnavailable = new ApiResponse()
              .description("Error Interno Controlado").content(new Content().addMediaType(
                  org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType()));

          apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()), apiResponseOk);
          apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
              apiResponseBadRequest);
          apiResponses.addApiResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
              apiResponseUnauthorized);
          apiResponses.addApiResponse(String.valueOf(HttpStatus.FORBIDDEN.value()),
              apiResponseForbidden);
          apiResponses.addApiResponse(String.valueOf(HttpStatus.NOT_FOUND.value()),
              apiResponseNotFound);
          apiResponses.addApiResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
              apiResponseInternalServerError);
          apiResponses.addApiResponse(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()),
              apiResponseServiceUnavailable);
        }));
  }
}
