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

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.adapter.SlingAdaptable;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Revision;
import com.day.cq.wcm.api.Template;
import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.api.msm.Blueprint;

/**
 * Mock implementation of {@link PageManager}
 */
@SuppressWarnings("deprecation")
class MockPageManager extends SlingAdaptable implements PageManager {

  private final ResourceResolver resourceResolver;

  public MockPageManager(final ResourceResolver resourceResolver) {
    this.resourceResolver = resourceResolver;
  }

  @Override
  public Page create(final String parentPath, final String pageName, final String template, final String title) //NOPMD
      throws WCMException { //NOPMD
    return create(parentPath, pageName, template, title, false);
  }

  @Override
  public Page create(final String parentPath, final String pageName, final String template, final String title, //NOPMD
      final boolean autoSave) throws WCMException { //NOPMD
    Resource parentResource = this.resourceResolver.getResource(parentPath);
    if (parentResource == null) {
      throw new WCMException(String.format("Parent path '%s' does not exist.", parentPath));
    }

    Resource pageResource;
    try {
      // page node
      Map<String, Object> props = new HashMap<String, Object>();
      props.put(JcrConstants.JCR_PRIMARYTYPE, NameConstants.NT_PAGE);
      pageResource = this.resourceResolver.create(parentResource, pageName, props);

      // page content node
      props = new HashMap<String, Object>();
      props.put(JcrConstants.JCR_PRIMARYTYPE, "cq:PageContent");
      props.put(JcrConstants.JCR_TITLE, title);
      props.put(NameConstants.PN_TEMPLATE, template);
      this.resourceResolver.create(pageResource, JcrConstants.JCR_CONTENT, props);

      if (autoSave) {
        this.resourceResolver.commit();
      }
    }
    catch (PersistenceException ex) {
      throw new WCMException("Creating page failed at :" + parentPath + "/" + pageName + " failed.", ex);
    }

    return pageResource.adaptTo(Page.class);
  }

  @Override
  public void delete(final Page page, final boolean shallow) throws WCMException {
    delete(page.adaptTo(Resource.class), shallow);
  }

  @Override
  public void delete(final Page page, final boolean shallow, final boolean autoSave) throws WCMException {
    delete(page.adaptTo(Resource.class), shallow, autoSave);
  }

  @Override
  public void delete(final Resource resource, final boolean shallow) throws WCMException {
    delete(resource, shallow, false);
  }

  @Override
  public void delete(final Resource resource, final boolean shallow, final boolean autoSave) throws WCMException {
    try {
      if (shallow) {
        Resource contentResource = resource.getChild(JcrConstants.JCR_CONTENT);
        if (contentResource != null) {
          this.resourceResolver.delete(contentResource);
        }
      }
      else {
        this.resourceResolver.delete(resource);
      }

      if (autoSave) {
        this.resourceResolver.commit();
      }
    }
    catch (PersistenceException ex) {
      throw new WCMException("Deleting resource at " + resource.getPath() + " failed.", ex);
    }
  }

  @Override
  public Page getContainingPage(final Resource resource) {
    if (resource == null) {
      return null;
    }
    Resource pageResource = resource;
    while (pageResource != null) {
      Page page = pageResource.adaptTo(Page.class);
      if (page != null) {
        return page;
      }
      pageResource = pageResource.getParent();
    }
    return null;
  }

  @Override
  public Page getContainingPage(final String path) {
    Resource resource = this.resourceResolver.getResource(path);
    return getContainingPage(resource);
  }

  @Override
  public Page getPage(final String path) {
    Resource resource = this.resourceResolver.getResource(path);
    if (resource != null) {
      return resource.adaptTo(Page.class);
    }
    return null;
  }

  @Override
  public Template getTemplate(final String templatePath) {
    Resource resource = this.resourceResolver.getResource(templatePath);
    if (resource != null) {
      return resource.adaptTo(Template.class);
    }
    else {
      return null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <AdapterType> AdapterType adaptTo(final Class<AdapterType> type) {
    if (type == ResourceResolver.class) {
      return (AdapterType)this.resourceResolver;
    }
    return super.adaptTo(type);
  }


  // --- unsupported operations ---

  @Override
  public Page move(final Page page, final String destination, final String beforeName, final boolean shallow,
      final boolean resolveConflict, final String[] adjustRefs) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Resource move(final Resource resource, final String destination, final String beforeName,
      final boolean shallow, final boolean resolveConflict, final String[] adjustRefs) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Page copy(final Page page, final String destination, final String beforeName, final boolean shallow,
      final boolean resolveConflict) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Page copy(final Page page, final String destination, final String beforeName, final boolean shallow,
      final boolean resolveConflict, final boolean autoSave) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Resource copy(final Resource resource, final String destination, final String beforeName,
      final boolean shallow, final boolean resolveConflict) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Resource copy(final Resource resource, final String destination, final String beforeName,
      final boolean shallow, final boolean resolveConflict, final boolean autoSave) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void order(final Page page, final String beforeName) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void order(final Page page, final String beforeName, final boolean autoSave) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void order(final Resource resource, final String beforeName) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void order(final Resource resource, final String beforeName, final boolean autoSave) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<Template> getTemplates(final String parentPath) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<Blueprint> getBlueprints(final String parentPath) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Revision createRevision(final Page page) throws WCMException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Revision createRevision(final Page page, final String label, final String comment) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<Revision> getRevisions(final String path, final Calendar cal) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<Revision> getRevisions(final String path, final Calendar cal, final boolean includeNoLocal) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<Revision> getChildRevisions(final String parentPath, final Calendar cal) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<Revision> getChildRevisions(final String parentPath, final Calendar cal, final boolean includeNoLocal) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<Revision> getChildRevisions(final String parentPath, final String treeRoot, final Calendar cal) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Page restore(final String path, final String revisionId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Page restoreTree(final String path, final Calendar date) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Page restoreTree(final String path, final Calendar date, final boolean preserveNV) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void touch(final Node page, final boolean shallow, final Calendar now, final boolean clearRepl) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Page move(Page page, String destination, String beforeName, boolean shallow, boolean resolveConflict,
      String[] adjustRefs, String[] publishRefs) throws WCMException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Resource move(Resource resource, String destination, String beforeName, boolean shallow,
      boolean resolveConflict, String[] adjustRefs, String[] publishRefs) throws WCMException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Resource copy(CopyOptions options) throws WCMException {
    throw new UnsupportedOperationException();
  }

}
