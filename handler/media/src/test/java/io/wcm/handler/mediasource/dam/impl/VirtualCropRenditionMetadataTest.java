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
package io.wcm.handler.mediasource.dam.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import io.wcm.handler.media.CropDimension;
import io.wcm.handler.media.Media;
import io.wcm.handler.mediasource.dam.AbstractDamTest;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.image.Layer;


public class VirtualCropRenditionMetadataTest extends AbstractDamTest {

  private Rendition rendition;
  private RenditionMetadata originalRendition;

  @Before
  public void setUp() throws Exception {
    String path = MEDIAITEM_PATH_STANDARD + "/jcr:content/renditions/cq5dam.web.215.102.jpg";
    context.resourceResolver().delete(context.resourceResolver().getResource(path));
    context.load().binaryFile("/sample_image_215x102.jpg", path);

    Media media = mediaHandler().get(MEDIAITEM_PATH_STANDARD).build();
    Asset asset = media.getAsset().adaptTo(Asset.class);
    rendition = asset.getRendition("cq5dam.web.215.102.jpg");
    originalRendition = new RenditionMetadata(asset.getRendition("original"));
  }

  @Test
  public void testGetLayer() throws Exception {
    VirtualCropRenditionMetadata underTest = new VirtualCropRenditionMetadata(rendition, 30, 25,
        new CropDimension(5, 5, 30, 25));

    Layer layer = underTest.getLayer();
    assertEquals(30, layer.getWidth());
    assertEquals(25, layer.getHeight());
  }

  @Test
  public void testGetLayer_resize() throws Exception {
    VirtualCropRenditionMetadata underTest = new VirtualCropRenditionMetadata(rendition, 30, 25,
        new CropDimension(5, 5, 60, 50));

    Layer layer = underTest.getLayer();
    assertEquals(30, layer.getWidth());
    assertEquals(25, layer.getHeight());
  }

  @Test
  public void testGetInputStream() throws Exception {
    VirtualCropRenditionMetadata underTest = new VirtualCropRenditionMetadata(rendition, 108, 51,
        new CropDimension(5, 5, 30, 25));

    InputStream is = underTest.getInputStream();
    assertNull(is);
  }

  @Test
  public void testEquals() {
    VirtualCropRenditionMetadata m1 = new VirtualCropRenditionMetadata(rendition, 108, 51,
        new CropDimension(5, 5, 30, 25));
    VirtualCropRenditionMetadata m2 = new VirtualCropRenditionMetadata(rendition, 108, 51,
        new CropDimension(5, 5, 30, 25));
    VirtualCropRenditionMetadata m3 = new VirtualCropRenditionMetadata(rendition, 108, 51,
        new CropDimension(10, 10, 30, 25));

    assertTrue(m1.equals(m2));
    assertFalse(m1.equals(m3));
  }

  @Test
  public void testCompareTo() {
    VirtualCropRenditionMetadata virtualRendition = new VirtualCropRenditionMetadata(rendition, 108, 51,
        new CropDimension(5, 5, 30, 25));
    RenditionMetadata biggerRendition = new RenditionMetadata(rendition);
    assertEquals(-1, virtualRendition.compareTo(originalRendition));
    assertEquals(-1, virtualRendition.compareTo(biggerRendition));
    assertEquals(1, originalRendition.compareTo(virtualRendition));
    assertEquals(1, biggerRendition.compareTo(virtualRendition));
  }

}
