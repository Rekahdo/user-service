package com.rekahdo.user_service.dtos.paginations;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.dtos.entities.EntityDto;
import com.rekahdo.user_service.utilities.StringFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.data.domain.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
@Setter
public abstract class PageRequestDto<ENTITY, DTO extends EntityDto<DTO>> implements Dto {

	protected Integer page = 0;

	protected Integer size = 10;

	protected boolean ascend = true;

	protected String sortByField = "id";

	public Pageable getPageable(){
		Sort.Direction sort = (isAscend() ? Sort.Direction.ASC : Sort.Direction.DESC);
		return PageRequest.of(page, size, sort, StringFormat.splitByComma(sortByField));
	}

	public Page<ENTITY> getPagedList(List<ENTITY> items){
		// PagedListHolder
		PagedListHolder<ENTITY> pagedListHolder = new PagedListHolder<>(items);
		pagedListHolder.setPage(this.getPage());
		pagedListHolder.setPageSize(this.getSize());

		// Property Comparator
		List<ENTITY> pageSlice = pagedListHolder.getPageList();
		boolean ascending = this.isAscend();
		PropertyComparator.sort(pageSlice, new MutableSortDefinition(this.getSortByField(), true, ascending));

		// PageImpl
		return new PageImpl<>(pageSlice, this.getPageable(), items.size());
	}

	public PagedModel<DTO> getPagedModel(Page<DTO> pageDto, Object invocationValue){
		PagedModel<DTO> pagedModel = PagedModel.of(pageDto.getContent(),
				new PagedModel.PageMetadata(pageDto.getSize(), pageDto.getNumber(),
						pageDto.getTotalElements(), pageDto.getTotalPages()
				)
		);

		if(pageDto.hasNext())
			pagedModel.add(buildLink(this.getPage()+1, "next", invocationValue));

		if(pageDto.hasPrevious())
			pagedModel.add(buildLink(this.getPage()-1, "prev", invocationValue));

		if(pageDto.getNumber() != 0)
			pagedModel.add(buildLink(0, "first", invocationValue));

		if(pageDto.getNumber() != pageDto.getTotalPages()-1){
			assert pagedModel.getMetadata() != null;
			int lastPageNo = (int)(pagedModel.getMetadata().getTotalPages()-1);
			pagedModel.add(buildLink(lastPageNo, "last", invocationValue));
		}

		return pagedModel;
	}

	private Link buildLink(Integer page, String relation, Object invocationValue) {
		UriComponentsBuilder builder = linkTo(invocationValue).toUriComponentsBuilder()
				.queryParam("page", page)
				.queryParam("size", this.getSize())
				.queryParam("ascend", this.isAscend())
				.queryParam("sortByField", this.getSortByField());
		return Link.of(builder.build().toUriString(), relation);
	}

}