package com.ckeiner.testbddy.core.bdd.status;

/**
 * Contains multiple Stati. Possible Stati are:
 * <li>{@link Status#IGNORE}</li>
 * <li>{@link Status#WIP}</li>
 * <li>{@link Status#SKIP}</li>
 * 
 * @author ckeiner
 *
 */
public enum Status
{
    /**
     * Implies that it should not be executed and not appear in the report
     */
    IGNORE,
    /**
     * Shows that it is a work in progress
     */
    WIP,
    /**
     * Implies that it should not be executed but appear in the report
     */
    SKIP,
    /**
     * Implies that there is nothing to execute, only a description
     */
    PENDING;
}
