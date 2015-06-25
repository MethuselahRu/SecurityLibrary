package ru.methuselah.securitylibrary.Data.GameFiles;

public class MojangAssetIndex
{
	public static class AssetObject
	{
		public String  originalName;
		public String  hash;
		public long    size;
		public boolean reconstruct;
		public String  compressedHash;
		public long    compressedSize;
		public String  sourceUrl;
		public boolean hasCompressedAlternative()
		{
			return compressedHash != null;
		}
		@Override
		public boolean equals(Object o)
		{
			if(this == o)
				return true;
			if((o == null) || (getClass() != o.getClass()))
				return false;
			AssetObject that = (AssetObject)o;
			if(compressedSize != that.compressedSize)
				return false;
			if(reconstruct != that.reconstruct)
				return false;
			if(size != that.size)
				return false;
			if(compressedHash != null ?  ! compressedHash.equals(that.compressedHash) : that.compressedHash != null)
				return false;
			return ! (hash != null ?  ! hash.equals(that.hash) : that.hash != null);
		}
		@Override
		public int hashCode()
		{
			int result = hash != null ? hash.hashCode() : 0;
			result = 31 * result + (int)(size ^ size >>> 32);
			result = 31 * result + (reconstruct ? 1 : 0);
			result = 31 * result + (compressedHash != null ? compressedHash.hashCode() : 0);
			result = 31 * result + (int)(compressedSize ^ compressedSize >>> 32);
			return result;
		}
	}
	public AssetObject[] objects = new AssetObject[] {};
	public boolean virtual = true;
}
