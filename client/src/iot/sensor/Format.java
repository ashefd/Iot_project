// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: format.proto

package iot.sensor;

public final class Format {
  private Format() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface UplinkOrBuilder extends
      // @@protoc_insertion_point(interface_extends:myobject.Uplink)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>float co2 = 1;</code>
     * @return The co2.
     */
    float getCo2();

    /**
     * <code>float temperature = 2;</code>
     * @return The temperature.
     */
    float getTemperature();

    /**
     * <code>float humidity = 3;</code>
     * @return The humidity.
     */
    float getHumidity();

    /**
     * <code>float loudness = 4;</code>
     * @return The loudness.
     */
    float getLoudness();

    /**
     * <pre>
     *google.protobuf.Timestamp timestamp = 6;
     * </pre>
     *
     * <code>int32 battery = 5;</code>
     * @return The battery.
     */
    int getBattery();
  }
  /**
   * Protobuf type {@code myobject.Uplink}
   */
  public static final class Uplink extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:myobject.Uplink)
      UplinkOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Uplink.newBuilder() to construct.
    private Uplink(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Uplink() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Uplink();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Uplink(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 13: {

              co2_ = input.readFloat();
              break;
            }
            case 21: {

              temperature_ = input.readFloat();
              break;
            }
            case 29: {

              humidity_ = input.readFloat();
              break;
            }
            case 37: {

              loudness_ = input.readFloat();
              break;
            }
            case 40: {

              battery_ = input.readInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return iot.sensor.Format.internal_static_myobject_Uplink_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return iot.sensor.Format.internal_static_myobject_Uplink_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              iot.sensor.Format.Uplink.class, iot.sensor.Format.Uplink.Builder.class);
    }

    public static final int CO2_FIELD_NUMBER = 1;
    private float co2_;
    /**
     * <code>float co2 = 1;</code>
     * @return The co2.
     */
    @java.lang.Override
    public float getCo2() {
      return co2_;
    }

    public static final int TEMPERATURE_FIELD_NUMBER = 2;
    private float temperature_;
    /**
     * <code>float temperature = 2;</code>
     * @return The temperature.
     */
    @java.lang.Override
    public float getTemperature() {
      return temperature_;
    }

    public static final int HUMIDITY_FIELD_NUMBER = 3;
    private float humidity_;
    /**
     * <code>float humidity = 3;</code>
     * @return The humidity.
     */
    @java.lang.Override
    public float getHumidity() {
      return humidity_;
    }

    public static final int LOUDNESS_FIELD_NUMBER = 4;
    private float loudness_;
    /**
     * <code>float loudness = 4;</code>
     * @return The loudness.
     */
    @java.lang.Override
    public float getLoudness() {
      return loudness_;
    }

    public static final int BATTERY_FIELD_NUMBER = 5;
    private int battery_;
    /**
     * <pre>
     *google.protobuf.Timestamp timestamp = 6;
     * </pre>
     *
     * <code>int32 battery = 5;</code>
     * @return The battery.
     */
    @java.lang.Override
    public int getBattery() {
      return battery_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (java.lang.Float.floatToRawIntBits(co2_) != 0) {
        output.writeFloat(1, co2_);
      }
      if (java.lang.Float.floatToRawIntBits(temperature_) != 0) {
        output.writeFloat(2, temperature_);
      }
      if (java.lang.Float.floatToRawIntBits(humidity_) != 0) {
        output.writeFloat(3, humidity_);
      }
      if (java.lang.Float.floatToRawIntBits(loudness_) != 0) {
        output.writeFloat(4, loudness_);
      }
      if (battery_ != 0) {
        output.writeInt32(5, battery_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (java.lang.Float.floatToRawIntBits(co2_) != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(1, co2_);
      }
      if (java.lang.Float.floatToRawIntBits(temperature_) != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(2, temperature_);
      }
      if (java.lang.Float.floatToRawIntBits(humidity_) != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(3, humidity_);
      }
      if (java.lang.Float.floatToRawIntBits(loudness_) != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(4, loudness_);
      }
      if (battery_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(5, battery_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof iot.sensor.Format.Uplink)) {
        return super.equals(obj);
      }
      iot.sensor.Format.Uplink other = (iot.sensor.Format.Uplink) obj;

      if (java.lang.Float.floatToIntBits(getCo2())
          != java.lang.Float.floatToIntBits(
              other.getCo2())) return false;
      if (java.lang.Float.floatToIntBits(getTemperature())
          != java.lang.Float.floatToIntBits(
              other.getTemperature())) return false;
      if (java.lang.Float.floatToIntBits(getHumidity())
          != java.lang.Float.floatToIntBits(
              other.getHumidity())) return false;
      if (java.lang.Float.floatToIntBits(getLoudness())
          != java.lang.Float.floatToIntBits(
              other.getLoudness())) return false;
      if (getBattery()
          != other.getBattery()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + CO2_FIELD_NUMBER;
      hash = (53 * hash) + java.lang.Float.floatToIntBits(
          getCo2());
      hash = (37 * hash) + TEMPERATURE_FIELD_NUMBER;
      hash = (53 * hash) + java.lang.Float.floatToIntBits(
          getTemperature());
      hash = (37 * hash) + HUMIDITY_FIELD_NUMBER;
      hash = (53 * hash) + java.lang.Float.floatToIntBits(
          getHumidity());
      hash = (37 * hash) + LOUDNESS_FIELD_NUMBER;
      hash = (53 * hash) + java.lang.Float.floatToIntBits(
          getLoudness());
      hash = (37 * hash) + BATTERY_FIELD_NUMBER;
      hash = (53 * hash) + getBattery();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static iot.sensor.Format.Uplink parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static iot.sensor.Format.Uplink parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static iot.sensor.Format.Uplink parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static iot.sensor.Format.Uplink parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static iot.sensor.Format.Uplink parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static iot.sensor.Format.Uplink parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static iot.sensor.Format.Uplink parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static iot.sensor.Format.Uplink parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static iot.sensor.Format.Uplink parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static iot.sensor.Format.Uplink parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static iot.sensor.Format.Uplink parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static iot.sensor.Format.Uplink parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(iot.sensor.Format.Uplink prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code myobject.Uplink}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:myobject.Uplink)
        iot.sensor.Format.UplinkOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return iot.sensor.Format.internal_static_myobject_Uplink_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return iot.sensor.Format.internal_static_myobject_Uplink_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                iot.sensor.Format.Uplink.class, iot.sensor.Format.Uplink.Builder.class);
      }

      // Construct using iot.sensor.Format.Uplink.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        co2_ = 0F;

        temperature_ = 0F;

        humidity_ = 0F;

        loudness_ = 0F;

        battery_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return iot.sensor.Format.internal_static_myobject_Uplink_descriptor;
      }

      @java.lang.Override
      public iot.sensor.Format.Uplink getDefaultInstanceForType() {
        return iot.sensor.Format.Uplink.getDefaultInstance();
      }

      @java.lang.Override
      public iot.sensor.Format.Uplink build() {
        iot.sensor.Format.Uplink result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public iot.sensor.Format.Uplink buildPartial() {
        iot.sensor.Format.Uplink result = new iot.sensor.Format.Uplink(this);
        result.co2_ = co2_;
        result.temperature_ = temperature_;
        result.humidity_ = humidity_;
        result.loudness_ = loudness_;
        result.battery_ = battery_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof iot.sensor.Format.Uplink) {
          return mergeFrom((iot.sensor.Format.Uplink)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(iot.sensor.Format.Uplink other) {
        if (other == iot.sensor.Format.Uplink.getDefaultInstance()) return this;
        if (other.getCo2() != 0F) {
          setCo2(other.getCo2());
        }
        if (other.getTemperature() != 0F) {
          setTemperature(other.getTemperature());
        }
        if (other.getHumidity() != 0F) {
          setHumidity(other.getHumidity());
        }
        if (other.getLoudness() != 0F) {
          setLoudness(other.getLoudness());
        }
        if (other.getBattery() != 0) {
          setBattery(other.getBattery());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        iot.sensor.Format.Uplink parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (iot.sensor.Format.Uplink) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private float co2_ ;
      /**
       * <code>float co2 = 1;</code>
       * @return The co2.
       */
      @java.lang.Override
      public float getCo2() {
        return co2_;
      }
      /**
       * <code>float co2 = 1;</code>
       * @param value The co2 to set.
       * @return This builder for chaining.
       */
      public Builder setCo2(float value) {
        
        co2_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>float co2 = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearCo2() {
        
        co2_ = 0F;
        onChanged();
        return this;
      }

      private float temperature_ ;
      /**
       * <code>float temperature = 2;</code>
       * @return The temperature.
       */
      @java.lang.Override
      public float getTemperature() {
        return temperature_;
      }
      /**
       * <code>float temperature = 2;</code>
       * @param value The temperature to set.
       * @return This builder for chaining.
       */
      public Builder setTemperature(float value) {
        
        temperature_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>float temperature = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearTemperature() {
        
        temperature_ = 0F;
        onChanged();
        return this;
      }

      private float humidity_ ;
      /**
       * <code>float humidity = 3;</code>
       * @return The humidity.
       */
      @java.lang.Override
      public float getHumidity() {
        return humidity_;
      }
      /**
       * <code>float humidity = 3;</code>
       * @param value The humidity to set.
       * @return This builder for chaining.
       */
      public Builder setHumidity(float value) {
        
        humidity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>float humidity = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearHumidity() {
        
        humidity_ = 0F;
        onChanged();
        return this;
      }

      private float loudness_ ;
      /**
       * <code>float loudness = 4;</code>
       * @return The loudness.
       */
      @java.lang.Override
      public float getLoudness() {
        return loudness_;
      }
      /**
       * <code>float loudness = 4;</code>
       * @param value The loudness to set.
       * @return This builder for chaining.
       */
      public Builder setLoudness(float value) {
        
        loudness_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>float loudness = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearLoudness() {
        
        loudness_ = 0F;
        onChanged();
        return this;
      }

      private int battery_ ;
      /**
       * <pre>
       *google.protobuf.Timestamp timestamp = 6;
       * </pre>
       *
       * <code>int32 battery = 5;</code>
       * @return The battery.
       */
      @java.lang.Override
      public int getBattery() {
        return battery_;
      }
      /**
       * <pre>
       *google.protobuf.Timestamp timestamp = 6;
       * </pre>
       *
       * <code>int32 battery = 5;</code>
       * @param value The battery to set.
       * @return This builder for chaining.
       */
      public Builder setBattery(int value) {
        
        battery_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *google.protobuf.Timestamp timestamp = 6;
       * </pre>
       *
       * <code>int32 battery = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearBattery() {
        
        battery_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:myobject.Uplink)
    }

    // @@protoc_insertion_point(class_scope:myobject.Uplink)
    private static final iot.sensor.Format.Uplink DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new iot.sensor.Format.Uplink();
    }

    public static iot.sensor.Format.Uplink getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Uplink>
        PARSER = new com.google.protobuf.AbstractParser<Uplink>() {
      @java.lang.Override
      public Uplink parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Uplink(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Uplink> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Uplink> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public iot.sensor.Format.Uplink getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_myobject_Uplink_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_myobject_Uplink_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014format.proto\022\010myobject\"_\n\006Uplink\022\013\n\003co" +
      "2\030\001 \001(\002\022\023\n\013temperature\030\002 \001(\002\022\020\n\010humidity" +
      "\030\003 \001(\002\022\020\n\010loudness\030\004 \001(\002\022\017\n\007battery\030\005 \001(" +
      "\005B\014\n\niot.sensorb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_myobject_Uplink_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_myobject_Uplink_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_myobject_Uplink_descriptor,
        new java.lang.String[] { "Co2", "Temperature", "Humidity", "Loudness", "Battery", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
