/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2018  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.sosy_lab.cpachecker.core.algorithm.mpv.partition;

import com.google.common.collect.ImmutableList;
import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.common.configuration.Option;
import org.sosy_lab.common.configuration.Options;
import org.sosy_lab.common.time.TimeSpan;
import org.sosy_lab.cpachecker.core.algorithm.mpv.property.MultipleProperties;

@Options(prefix = "mpv.joint")
public final class JointPartitioningOperator extends AbstractPartitioningOperator {

  @Option(
      secure = true,
      name = "firstPhaseRatio",
      description =
          "The ratio of CPU time limit in the first phase of Joint partitioning "
              + "to CPU time limit per each property.")
  private double firstPhaseRatio = 1.3;

  public JointPartitioningOperator(
      Configuration pConfiguration, MultipleProperties pProperties, TimeSpan pTimeLimitPerProperty)
      throws InvalidConfigurationException {
    super(pConfiguration, pProperties, pTimeLimitPerProperty);
    pConfiguration.inject(this);
  }

  @Override
  public ImmutableList<Partition> createPartition() {
    if (phase == 1) {
      phase = 2;
      return createJointPartition(properties, scaleTimeLimit(firstPhaseRatio), false);
    } else {
      return createSeparatedPartition(properties);
    }
  }
}
