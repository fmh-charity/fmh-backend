//package ru.iteco.fmh.dto.claim;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import ru.iteco.fmh.model.task.StatusE;
//
//import java.time.LocalDateTime;
////для Получение списка заявок из формы "Заявки"
//@ApiModel(description = "краткая информация по заявке")
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Data
//public class ClaimShortInfoDto {
//
//    @ApiModelProperty("идентификатор заявки")
//    private Integer id;
//    @ApiModelProperty("плановая дата исполнения заявки")
//    private LocalDateTime planExecuteDate;
//    @ApiModelProperty("фактическая дата исполнения заявки")
//    private LocalDateTime factExecuteDate;
//    @ApiModelProperty("исполнитель, в формате \"Кузнецова Н.П.\"")
//    private String shortExecutorName;
//    @ApiModelProperty("описание заявки")
//    private String description;
//    @ApiModelProperty("статус заявки")
//    private StatusE status;
//
//}
