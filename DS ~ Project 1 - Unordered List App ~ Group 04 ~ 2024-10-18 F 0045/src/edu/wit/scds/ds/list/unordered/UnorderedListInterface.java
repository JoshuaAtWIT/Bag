/* @formatter:off
 *
 * Dave Rosenberg
 * Comp 2000 - Data Structures
 * Demonstration: Unordered Lists
 * Fall, 2024
 *
 * Usage restrictions:
 *
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 *
 * Further, you may not post (including in a public repository such as on github)
 * nor otherwise share this code with anyone other than current students in my
 * sections of this course. Violation of these usage restrictions will be considered
 * a violation of the Wentworth Institute of Technology Academic Honesty Policy.
 *
 * Do not remove this notice.
 *
 * @formatter:on
 */


package edu.wit.scds.ds.list.unordered ;

/**
 * An interface that describes the operations of a bag of objects.
 *
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 *
 * @version 5.0
 *
 * @author David M Rosenberg
 *
 * @version 5.1.0 reformat per class standard
 * @version 6.0 2024-09-17 Rename to UnorderedList
 *
 * @param <T>
 *     The class of item an UnorderedList will hold.
 */
public interface UnorderedListInterface<T>
    {

    /**
     * Adds a new entry to this unordered list.
     *
     * @param newEntry
     *     The object to be added as a new entry.
     *
     * @return true if the addition is successful, or false if not.
     */
    public boolean add( T newEntry ) ;


    /**
     * Removes all entries from this unordered list.
     */
    public void clear() ;


    /**
     * Tests whether this unordered list contains a given entry.
     *
     * @param anEntry
     *     The entry to find.
     *
     * @return true if the unordered list contains anEntry, or false if not.
     */
    public boolean contains( T anEntry ) ;


    /**
     * Gets the current number of entries in this unordered list.
     *
     * @return The integer number of entries currently in the unordered list.
     */
    public int getCurrentSize() ;


    /**
     * Counts the number of times a given entry appears in this unordered list.
     *
     * @param anEntry
     *     The entry to be counted.
     *
     * @return The number of times anEntry appears in the unordered list.
     */
    public int getFrequencyOf( T anEntry ) ;


    /**
     * Sees whether this unordered list is empty.
     *
     * @return true if the unordered list is empty, or false if not.
     */
    public boolean isEmpty() ;


    /**
     * Removes one unspecified entry from this unordered list, if possible.
     *
     * @return Either the removed entry, if the removal was successful, or null.
     */
    public T remove() ;


    /**
     * Removes one occurrence of a given entry from this unordered list, if possible.
     *
     * @param anEntry
     *     The entry to be removed.
     *
     * @return true if the removal was successful, or false if not.
     */
    public boolean remove( T anEntry ) ;


    /**
     * Retrieves all entries that are in this unordered list.
     *
     * @return A newly allocated array of all the entries in the unordered list. Note: If the
     *     unordered list is empty, the returned array is empty.
     */
    public T[] toArray() ;

    } // end interface UnorderedListInterface