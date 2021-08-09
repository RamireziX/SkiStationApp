package edit_windows.client;

import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMap;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;

public class SkipassInfo {
	private Skipass skipass;
	private SkipassSkipassTypeMap sstm;
	private SkipassType skipassType;

	public SkipassInfo(Skipass skipass, SkipassSkipassTypeMap sstm,
					   SkipassType skipassType)
	{
		this.skipass = skipass;
		this.sstm = sstm;
		this.skipassType = skipassType;
	}

	public Skipass getSkipass()
	{
		return skipass;
	}

	public void setSkipass(Skipass skipass)
	{
		this.skipass = skipass;
	}

	public SkipassSkipassTypeMap getSstm()
	{
		return sstm;
	}

	public void setSstm(SkipassSkipassTypeMap sstm)
	{
		this.sstm = sstm;
	}

	public SkipassType getSkipassType()
	{
		return skipassType;
	}

	public void setSkipassType(SkipassType skipassType)
	{
		this.skipassType = skipassType;
	}
}
