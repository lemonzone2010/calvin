package com.apusic.ebiz.framework.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.CJKTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import com.apusic.ebiz.framework.core.fulltextsearch.filter.DoubleRangeFilterFactory;

/**
 *
 */
@AnalyzerDef(name = "customanalyzer",
		tokenizer = @TokenizerDef(factory = CJKTokenizerFactory.class),
		filters = {
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
						@Parameter(name = "language", value = "English")
				})
		})
@FullTextFilterDef(name = "priceFilter", impl = DoubleRangeFilterFactory.class)
@Indexed
public class DummyBook {

    private Integer id;

    @Field(index = Index.YES, store = Store.YES)
    @DateBridge(resolution = Resolution.DAY)
    private Date publicationDate;

    @Field(index = Index.YES, store = Store.YES)
    @Analyzer(definition = "customanalyzer")
    private String title;

    @Field(index = Index.YES, store = Store.NO)
    @Analyzer(definition = "customanalyzer")
    private String subtitle;

    @Field(index = Index.YES, store = Store.YES)
    @NumericField
    private Double price;

    private Set<DummyAuthor> authors = new HashSet<DummyAuthor>();



    @IndexedEmbedded
    public Set<DummyAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<DummyAuthor> authors) {
        this.authors = authors;
    }



    public DummyBook() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DocumentId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }


    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
