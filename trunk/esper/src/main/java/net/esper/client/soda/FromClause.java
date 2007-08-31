package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class FromClause implements Serializable
{
    private List<ProjectedStream> streams;
    private List<OuterJoinQualifier> outerJoinQualifiers;

    public static FromClause create(ProjectedStream stream)
    {
        return new FromClause(stream);
    }

    public static FromClause create(ProjectedStream stream, OuterJoinQualifier outerJoinQualifier, ProjectedStream streamSecond)
    {
        return new FromClause(stream, outerJoinQualifier, streamSecond);
    }

    public static FromClause create(ProjectedStream ...streams)
    {
        return new FromClause(streams);
    }

    public FromClause(ProjectedStream streamOne, OuterJoinQualifier outerJoinQualifier, ProjectedStream streamTwo)
    {
        this(streamOne);
        add(streamTwo);
        outerJoinQualifiers.add(outerJoinQualifier);
    }

    public FromClause(ProjectedStream stream)
    {
        streams = new ArrayList<ProjectedStream>();
        outerJoinQualifiers = new ArrayList<OuterJoinQualifier>();
        streams.add(stream);
    }

    public FromClause(ProjectedStream ...streamsList)
    {
        streams = new ArrayList<ProjectedStream>();
        outerJoinQualifiers = new ArrayList<OuterJoinQualifier>();
        for (int i = 0; i < streamsList.length; i++)
        {
            streams.add(streamsList[i]);
        }
    }

    public FromClause add(ProjectedStream stream)
    {
        streams.add(stream);
        return this;
    }

    public FromClause add(OuterJoinQualifier outerJoinQualifier)
    {
        outerJoinQualifiers.add(outerJoinQualifier);
        return this;
    }

    public List<ProjectedStream> getStreams()
    {
        return streams;
    }

    public void toEQL(StringWriter writer)
    {
        String delimiter = "";
        writer.write("from ");

        if (outerJoinQualifiers.size() == 0)
        {
            for (ProjectedStream stream : streams)
            {
                writer.write(delimiter);
                stream.toEQL(writer);
                delimiter = ", ";
            }
        }
        else
        {
            if (outerJoinQualifiers.size() != (streams.size() - 1))
            {
                throw new IllegalArgumentException("Number of outer join outerJoinQualifiers must be one less then the number of streams");
            }
            for (int i = 0; i < streams.size(); i++)
            {
                ProjectedStream stream = streams.get(i);
                stream.toEQL(writer);

                if (i > 0)
                {
                    OuterJoinQualifier qualCond = outerJoinQualifiers.get(i - 1);
                    writer.write(" on ");
                    qualCond.getLeft().toEQL(writer);
                    writer.write(" = ");
                    qualCond.getRight().toEQL(writer);
                }

                if (i < streams.size() - 1)
                {
                    OuterJoinQualifier qualType = outerJoinQualifiers.get(i);
                    writer.write(" ");
                    writer.write(qualType.getType().getText());
                    writer.write(" outer join ");
                }
            }
        }
    }

    public List<OuterJoinQualifier> getOuterJoinQualifiers()
    {
        return outerJoinQualifiers;
    }
}
