/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.bulk.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
@GraphQLName("SelectionScope")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SelectionScope")
public class SelectionScope {

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	@JsonIgnore
	public void setFolderId(
		UnsafeSupplier<Long, Exception> folderIdUnsafeSupplier) {

		try {
			folderId = folderIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long folderId;

	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

	@JsonIgnore
	public void setRepositoryId(
		UnsafeSupplier<Long, Exception> repositoryIdUnsafeSupplier) {

		try {
			repositoryId = repositoryIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long repositoryId;

	public Boolean getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(Boolean selectAll) {
		this.selectAll = selectAll;
	}

	@JsonIgnore
	public void setSelectAll(
		UnsafeSupplier<Boolean, Exception> selectAllUnsafeSupplier) {

		try {
			selectAll = selectAllUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean selectAll;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SelectionScope)) {
			return false;
		}

		SelectionScope selectionScope = (SelectionScope)object;

		return Objects.equals(toString(), selectionScope.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (folderId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"folderId\": ");

			sb.append(folderId);
		}

		if (repositoryId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"repositoryId\": ");

			sb.append(repositoryId);
		}

		if (selectAll != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectAll\": ");

			sb.append(selectAll);
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

}