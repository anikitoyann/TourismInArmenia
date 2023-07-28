package com.example.tourarmeniacommon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTourPackage is a Querydsl query type for TourPackage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourPackage extends EntityPathBase<TourPackage> {

    private static final long serialVersionUID = 1273277554L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTourPackage tourPackage = new QTourPackage("tourPackage");

    public final QCar car;

    public final StringPath duration = createString("duration");

    public final NumberPath<Integer> groupSize = createNumber("groupSize", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QItem item;

    public final StringPath name = createString("name");

    public final StringPath picName = createString("picName");

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final QRegion region;

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public QTourPackage(String variable) {
        this(TourPackage.class, forVariable(variable), INITS);
    }

    public QTourPackage(Path<? extends TourPackage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTourPackage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTourPackage(PathMetadata metadata, PathInits inits) {
        this(TourPackage.class, metadata, inits);
    }

    public QTourPackage(Class<? extends TourPackage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.car = inits.isInitialized("car") ? new QCar(forProperty("car")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.region = inits.isInitialized("region") ? new QRegion(forProperty("region")) : null;
    }

}

