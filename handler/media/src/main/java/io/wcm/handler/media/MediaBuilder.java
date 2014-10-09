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
package io.wcm.handler.media;

import io.wcm.handler.commons.dom.HtmlElement;
import io.wcm.handler.media.args.MediaArgsType;
import io.wcm.handler.media.format.MediaFormat;
import io.wcm.handler.url.UrlMode;

/**
 * Define media handling request using builder pattern.
 */
public interface MediaBuilder {

  /**
   * Sets additional arguments affection media resolving e.g. media formats.
   * @param mediaArgs Media arguments
   * @return Media builder
   */
  MediaBuilder args(MediaArgsType mediaArgs);

  /**
   * Set media format(s).
   * It is not allowed to set both media args and Media formats at the same time.
   * @param mediaFormats Media format(s)
   * @return Media builder
   */
  MediaBuilder mediaFormat(MediaFormat... mediaFormats);

  /**
   * Sets the name of the property from which the media request is read, or node name for inline media.
   * @param refProperty Property or node name
   * @return Media builder
   */
  MediaBuilder refProperty(String refProperty);

  /**
   * Set the name of the property which contains the cropping parameters.
   * @param cropProperty Property name
   * @return Media builder
   */
  MediaBuilder cropProperty(String cropProperty);

  /**
   * Sets URL mode for resolving the media URLs. This is a shortcut instead setting it in the media arguments.
   * It is not allowed to set both media args and URL mode at the same time.
   * @param urlMode URL mode
   * @return Media builder
   */
  MediaBuilder urlMode(UrlMode urlMode);

  /**
   * Resolve media and return metadata objects that contains all results.
   * @return Media metadata object. Never null, if the resolving failed the isValid() method returns false.
   */
  Media build();

  /**
   * Resolve media and return directly the markup generated by the media markup builder.
   * @return HTML markup string (may by an img, div or any other element) or null if resolving was not successful.
   */
  String buildMarkup();

  /**
   * Resolve media and return directly the markup as DOM element generated by the media markup builder.
   * @return HTML element (may by an img, div or any other element) or null if resolving was not successful.
   */
  HtmlElement<?> buildElement();

  /**
   * Resolve media and get URL to reference it directly.
   * @return URL pointing to media object or null if resolving was not successful.
   */
  String buildUrl();

}
