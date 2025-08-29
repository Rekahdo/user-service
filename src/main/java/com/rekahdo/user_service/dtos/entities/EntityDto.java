package com.rekahdo.user_service.dtos.entities;

import com.rekahdo.user_service.dtos.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "items")
public class EntityDto<T extends EntityDto<T>> extends RepresentationModel<T> implements Dto {

}