package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
/*
{
    "data": {
        "id": 4,
        "name": "aqua sky",
        "year": 2003,
        "color": "#7BC4C4",
        "pantone_value": "14-4811"
    }
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {
    private Integer id;
    private String name;
    private Integer year;
    private String color;
    @JsonProperty("pantone_value")
    private String pantoneValue;

}