package tu.sofia.cv.integration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

@SuppressWarnings("javadoc")
public class DateConverter implements Converter {

	private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").setLongSerializationPolicy(LongSerializationPolicy.STRING)
			.create();

	@Override
	public Object fromBody(TypedInput body, Type type) throws ConversionException {
		try {
			return GSON.fromJson(new InputStreamReader(body.in()), type);
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public TypedOutput toBody(Object object) {
		return new JsonTypedOutput(GSON.toJson(object));
	}

	private static class JsonTypedOutput implements TypedOutput {
		private final String json;

		JsonTypedOutput(String json) {
			this.json = json;
		}

		@Override
		public String fileName() {
			return null;
		}

		@Override
		public String mimeType() {
			return "application/json; charset=UTF-8";
		}

		@Override
		public long length() {
			return json.length();
		}

		@Override
		public void writeTo(OutputStream out) throws IOException {
			out.write(json.getBytes());
		}
	}
}
