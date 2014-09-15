package com.riaancornelius.flux.jira.domain.author;

import android.util.Log;

import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.CacheLoadingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Elsabe on 2014/02/08.
 */
public class AuthorList extends ArrayList<Author> {

    private static final String TAG = "AUTHORS";

    public AuthorList() {
        // Needed For JSON
        super();
    }

    public AuthorList(List<Author> authors) {
        super(authors);
    }

    public void cacheAuthors(SpiceManager spiceManager) {
        Log.d(TAG, "Caching authors");
        for (Author author : this) {
            author.putInCache(spiceManager);
        }
    }

    /**
     * Performance of this is not great. It does a lot of things in the background.
     *
     * @param spiceManager SpiceManager from the activity that needs this data.
     * @return List of all authors that was cached
     */
    public static AuthorList getFromCache(SpiceManager spiceManager) {
        Log.d(TAG, "Loading authors from cache");
        final ArrayList<Author> authors = new ArrayList<Author>();
        try {
            final Future<List<Author>> allDataFromCache = spiceManager.getAllDataFromCache(Author.class);
            if (allDataFromCache != null) {
                Log.d(TAG, "There was cached authors - Getting them");
                authors.addAll(allDataFromCache.get());
                Log.d(TAG, "Done getting cached authors");
            }
        } catch (CacheLoadingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ImmutableList<Author> filteredAuthors = ImmutableSet.copyOf(
                FluentIterable
                        .from(authors)
                        .filter(Predicates.notNull())
                        .filter(Author.notEmptyPredicate())
                        .toSortedList(Ordering.<Author>natural())).asList();

        return new AuthorList(filteredAuthors);
    }
}
