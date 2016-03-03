package guoyuan.webmining;

public class MetaItem implements Comparable
{
	private String engine = "";
	private String content = "";
	private double sorce = 0.0;
	private int    sequence = 0; 
	private String keyString = "";	
	public int compareTo(Object obj)
	{
		if(!(obj instanceof MetaItem))
		{
			throw new RuntimeException("对象类型错误");
		}
		MetaItem tempItem = (MetaItem)obj;
		
		if(this.sorce > tempItem.sorce)
			return -1;
		if(this.sorce == tempItem.sorce)
		{
			if(this.sequence > tempItem.sequence)
				return 1;
			if(this.sequence == tempItem.sequence)
			{
				if(this.getKeyString().length() > tempItem.getKeyString().length())
					return -1;
				if(this.getKeyString().length() == tempItem.getKeyString().length())
					return 0;
				else
					return 1;
			}
			else
				return -1;
		}
		else
		{
			return 1;
		}
		
	}
	public MetaItem(String engine, String content,
     int sequence, Double sorce,String keyString)
	{
		this.engine = engine;
		this.content = content;
		this.sequence = sequence;
		this.sorce = sorce;
		this.keyString = keyString;
		
	}
	public String getKeyString()
	{
		return keyString;
	}
	public String getEngine()
	{
		return engine;
	}	
	public double getSorce()
	{
		return sorce;
	}	
	public String getContent()
	{
		return content;
	}	
	public int getSequence()
	{
		return sequence;
	}		
	public void setSequence(int sequence)
	{
		this.sequence = sequence;
	}	
	public void setEngine(String engine)
	{
		this.engine = engine;
	}	
	public void setSorce(Double sorce)
	{
		this.sorce = sorce;
	}
	public void setContent( String content)
	{
		this.content = content;
	}	
}