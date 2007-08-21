package net.esper.client.soda;

public class SampleQueryBuilder
{
    public void build()
    {
        // select page, responseTime
        // from PageLoad
        // where page like '%Approve%' or responseTime > 100
        EPStatementObjectModel modelOne = new EPStatementObjectModel();
        modelOne.addSelectClause(SelectClause.create("page", "responseTime"))
                .addStream(FilterStream.create("PageLoad"))
                .addWhereClause(
                        Expressions.or(Expressions.like("page", "%Approve%"),
                                       Expressions.ge("responseTime", 100))
                            );
        
        // select page as mypage, avg(responseTime)
        // from PageLoad.win:time(30)
        EPStatementObjectModel modelTwo = new EPStatementObjectModel();
        modelTwo.addSelectClause(SelectClause.create()
                                    .add("page", "mypage")
                                    .add(Expressions.avg("responseTime")))
                .addStream(FilterStream.create("PageLoad").addView(View.create("win", "time", 30)));

        // insert into MyStream (propOne, propTwo)
        // select page, count(*) as pagecount from PageLoad.std:groupby('page').win:length(100)
        EPStatementObjectModel modelThree = new EPStatementObjectModel();
        modelThree.addInsertInto(InsertInto.create("MyStream", "propOne", "propTwo"))
                  .addSelectClause(SelectClause.create()
                                    .add("page")
                                    .add(Expressions.countStar(), "pagecount"))
                  .addStream(FilterStream.create("PageLoad")
                                .addView(View.create("std", "groupby", "page"))
                                .addView(View.create("win", "length", 100)));
    }
}
