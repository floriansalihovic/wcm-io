/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.testing.mock.aem;

import java.util.Collection;
import java.util.Map;

import org.apache.sling.api.adapter.SlingAdaptable;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.components.Component;
import com.day.cq.wcm.api.components.ComponentEditConfig;
import com.day.cq.wcm.api.components.VirtualComponent;

/**
 * Mock implementation of {@link Component}.
 */
class MockComponent extends SlingAdaptable implements Component {

  private final Resource resource;
  private final ValueMap props;

  public MockComponent(Resource resource) {
    this.resource = resource;
    this.props = resource.getValueMap();
  }

  @Override
  public String getPath() {
    return resource.getPath();
  }

  @Override
  public String getName() {
    return resource.getName();
  }

  @Override
  public String getTitle() {
    return props.get(JcrConstants.JCR_TITLE, String.class);
  }

  @Override
  public String getDescription() {
    return props.get(JcrConstants.JCR_DESCRIPTION, String.class);
  }

  @Override
  public ValueMap getProperties() {
    return props;
  }

  @Override
  public String getResourceType() {
    return resource.getResourceType();
  }

  @Override
  public boolean isAccessible() {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <AdapterType> AdapterType adaptTo(Class<AdapterType> type) {
    if (type == Resource.class) {
      return (AdapterType)resource;
    }
    return super.adaptTo(type);
  }


  // --- unsupported operations ---

  @Override
  public String getCellName() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isEditable() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isDesignable() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isContainer() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isAnalyzable() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean noDecoration() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getDialogPath() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getDesignDialogPath() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getIconPath() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getThumbnailPath() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getComponentGroup() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ComponentEditConfig getDeclaredEditConfig() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ComponentEditConfig getDeclaredChildEditConfig() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ComponentEditConfig getEditConfig() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ComponentEditConfig getChildEditConfig() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ComponentEditConfig getDesignEditConfig(String cellName) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String, String> getHtmlTagAttributes() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Component getSuperComponent() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Resource getLocalResource(String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<VirtualComponent> getVirtualComponents() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getDefaultView() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getTemplatePath() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String[] getInfoProviders() {
    throw new UnsupportedOperationException();
  }

}
