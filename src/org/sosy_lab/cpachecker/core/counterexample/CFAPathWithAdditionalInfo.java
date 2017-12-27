/*
 * CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2017  Dirk Beyer
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
 *
 *
 *  CPAchecker web page:
 *    http://cpachecker.sosy-lab.org
 */
package org.sosy_lab.cpachecker.core.counterexample;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sosy_lab.cpachecker.cfa.model.CFAEdge;
import org.sosy_lab.cpachecker.core.interfaces.ConfigurableProgramAnalysis;
import org.sosy_lab.cpachecker.core.interfaces.StateWithAdditionalInfo;
import org.sosy_lab.cpachecker.cpa.arg.ARGPath;
import org.sosy_lab.cpachecker.cpa.arg.ARGPath.PathIterator;
import org.sosy_lab.cpachecker.cpa.arg.ARGState;
import org.sosy_lab.cpachecker.util.AbstractStates;

public class CFAPathWithAdditionalInfo extends ForwardingList<CFAEdgeWithAdditionalInfo> {
  private final ImmutableList<CFAEdgeWithAdditionalInfo> pathInfo;

  public CFAPathWithAdditionalInfo(List<CFAEdgeWithAdditionalInfo> pPathInfo) {
    pathInfo = ImmutableList.copyOf(pPathInfo);
  }

  public static CFAPathWithAdditionalInfo of(ARGPath pPath, ConfigurableProgramAnalysis pCPA) {
    StateWithAdditionalInfo stateWithAdditionalInfo =
        AbstractStates.extractStateByType(pPath.getFirstState(), StateWithAdditionalInfo.class);

    CFAPathWithAdditionalInfo path = stateWithAdditionalInfo.createExtendedInfo(pPath);

    return path;

  }

  @Override
  protected List<CFAEdgeWithAdditionalInfo> delegate() {
    return pathInfo;
  }

  public Map<ARGState, CFAEdgeWithAdditionalInfo> getAdditionalInfoMapping(ARGPath pPath) {
    Map<ARGState, CFAEdgeWithAdditionalInfo> result = new HashMap<>();

    PathIterator pathIterator = pPath.fullPathIterator();
    int multiEdgeOffset = 0;

    while (pathIterator.hasNext()) {
      CFAEdgeWithAdditionalInfo edgeWithAdditionalInfo = pathInfo.get(pathIterator.getIndex() + multiEdgeOffset);
      CFAEdge argPathEdge = pathIterator.getOutgoingEdge();

      if (!edgeWithAdditionalInfo.getCFAEdge().equals(argPathEdge)) {
        // path is not equivalent
        return ImmutableMap.of();
      }

      final ARGState abstractState;
      if (pathIterator.isPositionWithState()) {
        abstractState = pathIterator.getAbstractState();
      } else {
        abstractState = pathIterator.getPreviousAbstractState();
      }
      result.put(abstractState, edgeWithAdditionalInfo);

      pathIterator.advance();
    }
    // last state is ignored

    return result;
  }
}
