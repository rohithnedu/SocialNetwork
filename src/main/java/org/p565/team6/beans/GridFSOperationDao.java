package org.p565.team6.beans;

import java.io.IOException;
import java.io.InputStream;

import org.p565.team6.model.RegisterModel;
import com.mongodb.gridfs.GridFSDBFile;

public interface GridFSOperationDao {

	public boolean insertOneFile(InputStream inputStream,String identifier)throws IOException;
	public GridFSDBFile searchOneFile(String identifier);
}
