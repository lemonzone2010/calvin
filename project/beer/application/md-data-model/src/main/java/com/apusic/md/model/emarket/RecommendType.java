package com.apusic.md.model.emarket;

public enum RecommendType {
    NEW("新品上架"),RECOMMEND("商城推荐"),HOT("畅销排行");

    private String type;

    public String getType() {
        return type;
    }

    private RecommendType(String type){
        this.type = type;
    }

    public static RecommendType toRecommendType(String type){
    	if(RecommendType.NEW.toString().equals(type)){
    		return RecommendType.NEW;
    	} else if(RecommendType.RECOMMEND.toString().equals(type)){
    		return RecommendType.RECOMMEND;
    	} else{
    		return RecommendType.HOT;
    	}
    }
}
