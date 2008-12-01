package cpa.common.interfaces;

import java.util.Collection;

import cpa.common.interfaces.AbstractElement;

import exceptions.CPAException;

public interface StopOperator
{
    public boolean stop (AbstractElement element, Collection<AbstractElement> reached) throws CPAException;
    public boolean stop (AbstractElement element, AbstractElement reachedElement) throws CPAException;
}
