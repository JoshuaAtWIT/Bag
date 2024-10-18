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

package edu.wit.scds.ds.list.unordered.adt ;

import edu.wit.scds.ds.list.unordered.UnorderedListInterface ;
import static edu.wit.scds.ds.list.unordered.app.GroceryBagLimits.GROCERY_BAG_MAX_ITEM_COUNT ;

import java.util.Arrays ;

/**
 * Fixed-size array-backed implementation of an unordered list.
 *
 * @author David M Rosenberg
 *
 * @version 1.0 2024-09-20 Initial implementation
 * @version 1.1 2024-10-06 back-fill from resizable v2 implementation
 *     <ul>
 *     <li>consolidate code
 *     <li>switch to JCL methods where applicable
 *     </ul>
 * @version 1.2 2024-10-09 change capacity limits for assignment
 *
 * @param <T>
 *     the type of all elements in an instance of an {@code ArrayBag}
 *
 * @since 1.0
 */
public final class ArrayBag<T> implements UnorderedListInterface<T>
    {

    /*
     * constants
     */
    /** the default capacity of an unordered list if not explicitly specified */
    private final static int DEFAULT_CAPACITY = 1 ;

    /** the largest number of entries an unordered list can hold */
    private final static int MAX_CAPACITY = GROCERY_BAG_MAX_ITEM_COUNT ;

    /*
     * data fields
     */
    private final T[] bag ;
    private int numberOfEntries ;

    private boolean integrityOK = false ;


    /*
     * constructors
     */


    /**
     * Initialize the state of an {@code ArrayBag} with default capacity
     *
     * @since 1.0
     */
    public ArrayBag()
        {

        this( DEFAULT_CAPACITY ) ;

        }   // end no-arg constructor


    /**
     * Initialize the state of an {@code ArrayBag} with application-specified capacity
     *
     * @param initialCapacity
     *     the maximum number of data items the the {@code ArrayBag} will be able to hold
     *
     * @since 1.0
     */
    public ArrayBag( final int initialCapacity )
        {

        // instance state is not yet valid
        this.integrityOK = false ;

        // validate the initial capacity
        checkCapacity( initialCapacity ) ;

        // the cast is safe because the new array is null filled
        @SuppressWarnings( "unchecked" )
        final T[] tempBag = (T[]) new Object[ initialCapacity ] ;
        this.bag = tempBag ;

        this.numberOfEntries = 0 ;

        // instance state is now valid
        this.integrityOK = true ;

        }   // end 1-arg constructor


    /*
     * API methods
     */


    @Override
    public boolean add( final T newEntry )
        {

        // make sure the array is intact and usable
        checkIntegrity() ;

        // reject null as data
        // check for available space
        if ( ( newEntry == null ) || isArrayFull() )
            {
            return false ;  // no room
            }

        // there is room so add the new entry in the next available location in the array
        this.bag[ this.numberOfEntries ] = newEntry ;
        this.numberOfEntries++ ;

        return true ;   // success

        }   // end add()


    @Override
    public void clear()
        {

        // make sure the array is intact and usable
        checkIntegrity() ;

        // remove each item individually
        while ( !isEmpty() )
            {
            remove() ;
            }

        }   // end clear()


    @Override
    public boolean contains( final T anEntry )
        {

        // make sure the array is intact and usable
        checkIntegrity() ;

        return getIndexOf( anEntry ) >= 0 ;

        }   // end contains()


    @Override
    public int getCurrentSize()
        {

        // not essential here since not touching the array - included for consistency across all API
        // methods
        checkIntegrity() ;

        return this.numberOfEntries ;

        }   // end getCurrentSize()


    @Override
    public int getFrequencyOf( final T anEntry )
        {

        // make sure the array is intact and usable
        checkIntegrity() ;

        // special case for null - we don't store them so can't find any
        if ( anEntry == null )
            {
            return 0 ;
            }

        int timesSeen = 0 ; // haven't seen any yet

        // check each element in the array for a match
        for ( int i = 0 ; i < this.numberOfEntries ; i++ )
            {

            if ( this.bag[ i ].equals( anEntry ) )
                {
                // found a match - count it
                timesSeen++ ;
                }

            }   // end for

        return timesSeen ;

        }   // end getFrequencyOf()


    @Override
    public boolean isEmpty()
        {

        // not essential here since not touching the array - included for consistency across all API
        // methods
        checkIntegrity() ;

        return this.numberOfEntries == 0 ;

        }   // end isEmpty()


    @Override
    public T remove()
        {

        // make sure the array is intact and usable
        checkIntegrity() ;

        return removeEntry( this.numberOfEntries - 1 ) ;

        }   // end no-arg/unspecified remove()


    @Override
    public boolean remove( final T anEntry )
        {

        // make sure the array is intact and usable
        checkIntegrity() ;

        // find the first matching entry
        final int index = getIndexOf( anEntry ) ;

        // if found, replace it with the last entry then remove the last entry
        return removeEntry( index ) != null ;   // indicate success/failure

        }   // end 1-arg/specified remove()


    @Override
    public T[] toArray()
        {

        // make sure the array is intact and usable
        checkIntegrity() ;

        return Arrays.copyOf( this.bag, this.numberOfEntries ) ;

        }   // end toArray()


    /*
     * private utility methods
     */


    /**
     * ensure the specified desired capacity is acceptable
     *
     * @param desiredCapacity
     *     the capacity specification to validate
     *
     * @throws IllegalStateException
     *     occurs when the desired capacity is outside the acceptable limits
     *
     * @since 1.0
     */
    private static void checkCapacity( final int desiredCapacity )
        throws IllegalStateException
        {

        // zero is not acceptable because doubling it won't increase the size of the array
        if ( desiredCapacity <= 0 )
            {
            throw new IllegalStateException( String.format( "desired capacity is too small: %,d",
                                                            desiredCapacity ) ) ;
            }

        if ( desiredCapacity > MAX_CAPACITY )
            {
            throw new IllegalStateException( String.format( "desired capacity is too large: %,d",
                                                            desiredCapacity ) ) ;
            }

        }   // end checkCapacity()


    /**
     * prevent continued execution unless the {@code ArrayBag}'s state is valid
     *
     * @throws SecurityException
     *     occurs when the instance is not in a valid, usable state
     *
     * @since 1.0
     */
    private void checkIntegrity() throws SecurityException
        {

        if ( !this.integrityOK )
            {
            throw new SecurityException( "unusable state detected" ) ;
            }

        }   // end checkIntegrity()


    /**
     * locate the first entry that matches the argument
     *
     * @param anEntry
     *     the entry to find
     *
     * @return the index of the first occurrence of {@code anEntry} if found, or -1 if not found
     *
     * @since 1.1
     */
    private int getIndexOf( final T anEntry )
        {

        // special case for null - we don't store them so can't find any
        if ( anEntry == null )
            {
            return -1 ;
            }

        for ( int i = 0 ; i < this.numberOfEntries ; i++ )
            {

            if ( this.bag[ i ].equals( anEntry ) )
                {
                return i ;  // found it
                }

            }

        return -1 ;  // didn't find it

        }   // end getIndexOf()


    /**
     * test the {@code ArrayBag}'s capacity for (lack of) room to add another entry
     *
     * @return {@code true} if all elements of the array are in use, {@code false} if there's at
     *     least one unused element
     *
     * @since 1.0
     */
    private boolean isArrayFull()
        {

        return this.bag.length == this.numberOfEntries ;

        }   // end isArrayFull()


    /**
     * remove and return the entry at the specified index
     *
     * @param givenIndex
     *     the index of the entry to remove/return
     *
     * @return the removed entry or {@code null} if {@code givenIndex} is negative
     *
     * @since 1.1
     */
    private T removeEntry( final int givenIndex )
        {

        T result = null ;   // haven't removed anything yet

        if ( !isEmpty() && ( givenIndex >= 0 ) )
            {
            result = this.bag[ givenIndex ] ;

            final int lastIndex = this.numberOfEntries - 1 ;

            this.bag[ givenIndex ] = this.bag[ lastIndex ] ;
            this.bag[ lastIndex ] = null ;

            this.numberOfEntries-- ;
            }

        return result ;

        }   // end removeEntry()

    }   // end class ArrayBag