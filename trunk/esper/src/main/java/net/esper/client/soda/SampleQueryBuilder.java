package net.esper.client.soda;

public class SampleQueryBuilder
{
    public void build()
    {
        // select page, responseTime
        // from PageLoad
        // where page like '%Approve%' or responseTime > 100
        EPStatementObjectModel modelOne = new EPStatementObjectModel();
        modelOne.setSelectClause(SelectClause.create("page", "responseTime"))
                .setFromClause(FromClause.create(FilterStream.create("PageLoad")))
                .setWhereClause(
                        Expressions.or(Expressions.like("page", "%Approve%"),
                                       Expressions.ge("responseTime", 100))
                            );
        
        // select page as mypage, avg(responseTime)
        // from PageLoad.win:time(30)
        EPStatementObjectModel modelTwo = new EPStatementObjectModel();
        modelTwo.setSelectClause(SelectClause.create()
                                    .add("page", "mypage")
                                    .add(Expressions.avg("responseTime")))
                .setFromClause(FromClause.create((FilterStream.create("PageLoad").addView(View.create("win", "time", 30)))));

        // insert into MyStream (propOne, propTwo)
        // select page, count(*) as pagecount from PageLoad.std:groupby('page').win:length(100)
        EPStatementObjectModel modelThree = new EPStatementObjectModel();
        modelThree.setInsertInto(InsertInto.create("MyStream", "propOne", "propTwo"))
                  .setSelectClause(SelectClause.create()
                                    .add("page")
                                    .add(Expressions.countStar(), "pagecount"))
                  .setFromClause(FromClause.create(FilterStream.create("PageLoad")
                                .addView(View.create("std", "groupby", "page"))
                                .addView(View.create("win", "length", 100))));
    }
}
