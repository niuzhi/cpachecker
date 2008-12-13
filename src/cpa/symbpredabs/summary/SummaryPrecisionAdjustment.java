/**
 * 
 */
package cpa.symbpredabs.summary;

import java.util.Collection;

import common.Pair;

import cpa.common.interfaces.AbstractElement;
import cpa.common.interfaces.Precision;
import cpa.common.interfaces.PrecisionAdjustment;

/**
 * @author Michael Tautschnig <tautschnig@forsyte.de>
 *
 */
public class SummaryPrecisionAdjustment implements PrecisionAdjustment {

  /* (non-Javadoc)
   * @see cpa.common.interfaces.PrecisionAdjustment#prec(cpa.common.interfaces.AbstractElement, cpa.common.interfaces.Precision, java.util.Collection)
   */
  public <AE extends AbstractElement> Pair<AE, Precision> prec(
                                                               AE pElement,
                                                               Precision pPrecision,
                                                               Collection<Pair<AE, Precision>> pElements) {
    // TODO Auto-generated method stub
    return null;
  }

}
