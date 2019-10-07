/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2019  Dirk Beyer
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
package org.sosy_lab.cpachecker.cpa.collector;


import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.sosy_lab.common.UniqueIdGenerator;
import org.sosy_lab.common.log.LogManager;
import org.sosy_lab.cpachecker.core.interfaces.AbstractState;
import org.sosy_lab.cpachecker.core.interfaces.Graphable;
import org.sosy_lab.cpachecker.cpa.arg.ARGState;

public class myARGState implements Graphable {
  private final LogManager logger;
  private final Collection<ARGState> myparents = new ArrayList<>(1);
  private final int currentID;
  private final AbstractState wrappedelement;
  private final Collection<ARGState> currentChildren;
  private AbstractState parentwrapped;
  private ARGState parentelement;
  private Collection<ARGState> myparentsCollection;
  private ARGState element;
  private static final UniqueIdGenerator idGenerator = new UniqueIdGenerator();
  private final int stateId;

  public myARGState(ARGState cElement,
                    @Nullable ARGState pParentElement,
                    @Nullable Collection<ARGState> cParents,
                    LogManager clogger){
    logger = clogger;
    stateId = idGenerator.getFreshId();
    element = cElement;
    wrappedelement = element.getWrappedState();
    currentID = element.getStateId();
    currentChildren = element.getChildren();

    if (cParents != null) {
      myparentsCollection = cParents;
    }
    if (pParentElement != null) {
      addParent(pParentElement);
      parentelement = pParentElement;
      parentwrapped = parentelement.getWrappedState();
    }
  }

  public void addParent(ARGState pOtherParent) {
    checkNotNull(pOtherParent);
    // Manually enforce set semantics.
    if (!myparents.contains(pOtherParent)) {
     // assert !pOtherParent.children.contains(this);
      myparents.add(pOtherParent);
      //pOtherParent.children.add();
    }
  }

  @Override
  public String toDOTLabel() {
    StringBuilder sb = new StringBuilder();

    sb.append("myARG State (Id: ");
    //sb.append(stateId);
    sb.append(currentID);
      if (myparents != null){
        sb.append(", myParents: ");
      sb.append(stateIdsOf(myparents));
      }
      sb.append(", myChildren: ");
      sb.append(stateIdsOf(currentChildren));
      sb.append(") ");
    //sb.append(element);
    sb.append(wrappedelement);
    return sb.toString();
  }

  @Override
  public boolean shouldBeHighlighted() {
    return false;
  }
  private Iterable<Integer> stateIdsOf(Iterable<ARGState> elements) {
    return from(elements).transform(ARGState::getStateId);
  }
  public ARGState getARGState() {
    return element;
  }
  public ARGState getparentARGState() {
    return parentelement;
  }
  public AbstractState getwrappedState() { return wrappedelement; }
 public AbstractState getwrappedParentState() { return parentwrapped; }
}