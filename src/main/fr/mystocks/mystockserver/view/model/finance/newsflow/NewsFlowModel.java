package fr.mystocks.mystockserver.view.model.finance.newsflow;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.mystocks.mystockserver.data.finance.newsflow.NewsFlow;
import fr.mystocks.mystockserver.technic.serializer.LocalDateTimeSerializer;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class NewsFlowModel {

	@JsonProperty("lastModified")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastModified;

	@JsonProperty("firstInput")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime firstInput;

	private String name;

	private String url;

	private String keyword;

	private Boolean notification;

	private Integer id;

	@JsonCreator
	public NewsFlowModel(LocalDateTime lastModified, LocalDateTime firstInput, LocalDateTime lastConnection,
			String name, String url, String keyword, Boolean notification, Integer id) {
		super();
		this.lastModified = lastModified;
		this.firstInput = firstInput;
		this.name = name;
		this.url = url;
		this.keyword = keyword;
		this.notification = notification;
		this.id = id;
	}

	@JsonCreator
	public NewsFlowModel() {
		super();
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public LocalDateTime getFirstInput() {
		return firstInput;
	}

	public void setFirstInput(LocalDateTime firstInput) {
		this.firstInput = firstInput;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Boolean getNotification() {
		return notification;
	}

	public void setNotification(Boolean notification) {
		this.notification = notification;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void convertFromNewsFlow(NewsFlow newsFlow) {
		BeanUtils.copyProperties(newsFlow, this);
	}

}
