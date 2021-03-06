/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2015  Dirk Beyer
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
package org.sosy_lab.cpachecker.cpa.argReplay;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.sosy_lab.cpachecker.cfa.model.CFAEdge;
import org.sosy_lab.cpachecker.core.defaults.SingleEdgeTransferRelation;
import org.sosy_lab.cpachecker.core.interfaces.AbstractState;
import org.sosy_lab.cpachecker.core.interfaces.Precision;
import org.sosy_lab.cpachecker.cpa.arg.ARGState;

public class ARGReplayTransferRelation extends SingleEdgeTransferRelation {

  public ARGReplayTransferRelation(){}

  @Override
  public Collection<? extends AbstractState> getAbstractSuccessorsForEdge(
      AbstractState pState, Precision pPrecision, CFAEdge pCfaEdge) {

    Set<ARGState> baseStates = ((ARGReplayState)pState).getStates();
    Set<ARGState> successors = new HashSet<>();

    // collect all states reachable via the edge
    for (ARGState baseState : baseStates) {

      // direct children
      getChildren(pCfaEdge, baseState, successors);

      // children of covering state
      // if (baseState.isCovered()) {
      //  ARGState coveringState = baseState.getCoveringState();
      //  logger.log(Level.INFO, "jumping from", pState, "to covering state", coveringState, "because of edge", pCfaEdge);
      //  getChildren(pCfaEdge, coveringState, successors);
      // }
    }

    return ImmutableSet.of(new ARGReplayState(successors, ((ARGReplayState) pState).getCPA()));
  }

  private void getChildren(CFAEdge pCfaEdge, ARGState baseState, Set<ARGState> successors) {
    for (ARGState child : baseState.getChildren()) {
      // normally only one child has the correct edge
      if (pCfaEdge.equals(baseState.getEdgeToChild(child))) {
        // redirect edge from child to covering state, because the subgraph of the covering state is reachable from there
        if (child.isCovered()) {
          successors.add(child.getCoveringState());
        } else {
          successors.add(child);
        }
      }
    }
  }
}
