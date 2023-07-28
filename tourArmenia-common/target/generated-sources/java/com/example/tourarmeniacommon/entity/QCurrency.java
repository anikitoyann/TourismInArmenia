package com.example.tourarmeniacommon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCurrency is a Querydsl query type for Currency
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCurrency extends EntityPathBase<Currency> {

    private static final long serialVersionUID = 694441197L;

    public static final QCurrency currency = new QCurrency("currency");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.util.Date> lastUpdatedDate = createDateTime("lastUpdatedDate", java.util.Date.class);

    public final NumberPath<Double> rub = createNumber("rub", Double.class);

    public final NumberPath<Double> usd = createNumber("usd", Double.class);

    public QCurrency(String variable) {
        super(Currency.class, forVariable(variable));
    }

    public QCurrency(Path<? extends Currency> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCurrency(PathMetadata metadata) {
        super(Currency.class, metadata);
    }

}

