/*
 * Copyright 2000-2011 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.util.download.impl;

import com.intellij.facet.frameworks.beans.Artifact;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.download.DownloadableFileDescription;
import com.intellij.util.download.DownloadableFileService;
import com.intellij.util.download.DownloadableFileSetDescription;
import com.intellij.util.download.DownloadableFileSetVersions;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.net.URL;
import java.util.List;

/**
 * @author nik
 */
public class DownloadableFileServiceImpl extends DownloadableFileService {
  @NotNull
  @Override
  public DownloadableFileDescription createFileDescription(@NotNull String downloadUrl, @NotNull String fileName) {
    return new DownloadableFileDescriptionImpl(downloadUrl, FileUtil.getNameWithoutExtension(fileName), FileUtil.getExtension(fileName));
  }

  @NotNull
  @Override
  public DownloadableFileSetVersions<DownloadableFileSetDescription> createFileSetVersions(@NotNull String groupId,
                                                                                           @NotNull URL... localUrls) {
    return new FileSetVersionsFetcherBase<DownloadableFileSetDescription>(groupId, localUrls) {
      @Override
      protected DownloadableFileSetDescription createVersion(Artifact version, List<DownloadableFileDescription> files) {
        return new DownloadableFileSetDescriptionImpl(version.getName(), version.getVersion(), files);
      }
    };
  }

  @Override
  public void loadVersionsToCombobox(@NotNull DownloadableFileSetVersions<?> versions, @NotNull JComboBox comboBox) {
  }
}
