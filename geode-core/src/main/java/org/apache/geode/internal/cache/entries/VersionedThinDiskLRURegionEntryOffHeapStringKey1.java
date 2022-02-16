

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.internal.cache.entries;

// DO NOT modify this class. It was generated from LeafRegionEntry.cpp
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

import org.apache.geode.cache.EntryEvent;
import org.apache.geode.distributed.internal.membership.InternalDistributedMember;
import org.apache.geode.internal.cache.DiskId;
import org.apache.geode.internal.cache.DiskStoreImpl;
import org.apache.geode.internal.cache.InternalRegion;
import org.apache.geode.internal.cache.PlaceHolderDiskRegion;
import org.apache.geode.internal.cache.RegionEntry;
import org.apache.geode.internal.cache.RegionEntryContext;
import org.apache.geode.internal.cache.Token;
import org.apache.geode.internal.cache.eviction.EvictionController;
import org.apache.geode.internal.cache.eviction.EvictionNode;
import org.apache.geode.internal.cache.persistence.DiskRecoveryStore;
import org.apache.geode.internal.cache.versions.VersionSource;
import org.apache.geode.internal.cache.versions.VersionStamp;
import org.apache.geode.internal.cache.versions.VersionTag;
import org.apache.geode.internal.offheap.OffHeapRegionEntryHelper;
import org.apache.geode.internal.offheap.annotations.Released;
import org.apache.geode.internal.offheap.annotations.Retained;
import org.apache.geode.internal.offheap.annotations.Unretained;
import org.apache.geode.internal.util.concurrent.CustomEntryConcurrentHashMap.HashEntry;

/*
 * macros whose definition changes this class:
 *
 * disk: DISK lru: LRU stats: STATS versioned: VERSIONED offheap: OFFHEAP
 *
 * One of the following key macros must be defined:
 *
 * key object: KEY_OBJECT key int: KEY_INT key long: KEY_LONG key uuid: KEY_UUID key string1:
 * KEY_STRING1 key string2: KEY_STRING2
 */
/**
 * Do not modify this class. It was generated. Instead modify LeafRegionEntry.cpp and then run
 * ./dev-tools/generateRegionEntryClasses.sh (it must be run from the top level directory).
 */
public class VersionedThinDiskLRURegionEntryOffHeapStringKey1
    extends VersionedThinDiskLRURegionEntryOffHeap {
  // --------------------------------------- common fields ----------------------------------------
  private static final AtomicLongFieldUpdater<VersionedThinDiskLRURegionEntryOffHeapStringKey1> LAST_MODIFIED_UPDATER =
      AtomicLongFieldUpdater.newUpdater(VersionedThinDiskLRURegionEntryOffHeapStringKey1.class,
          "lastModified");
  protected int hash;
  private HashEntry<Object, Object> nextEntry;
  @SuppressWarnings("unused")
  private volatile long lastModified;
  // --------------------------------------- offheap fields ---------------------------------------
  /**
   * All access done using OFF_HEAP_ADDRESS_UPDATER so it is used even though the compiler can not
   * tell it is.
   */
  @SuppressWarnings("unused")
  @Retained
  @Released
  private volatile long offHeapAddress;
  /**
   * I needed to add this because I wanted clear to call setValue which normally can only be called
   * while the re is synced. But if I sync in that code it causes a lock ordering deadlock with the
   * disk regions because they also get a rw lock in clear. Some hardware platforms do not support
   * CAS on a long. If gemfire is run on one of those the AtomicLongFieldUpdater does a sync on the
   * RegionEntry and we will once again be deadlocked. I don't know if we support any of the
   * hardware platforms that do not have a 64bit CAS. If we do then we can expect deadlocks on disk
   * regions.
   */
  private static final AtomicLongFieldUpdater<VersionedThinDiskLRURegionEntryOffHeapStringKey1> OFF_HEAP_ADDRESS_UPDATER =
      AtomicLongFieldUpdater.newUpdater(VersionedThinDiskLRURegionEntryOffHeapStringKey1.class,
          "offHeapAddress");
  // ---------------------------------------- disk fields -----------------------------------------
  /**
   * @since GemFire 5.1
   */
  protected DiskId id;
  // ------------------------------------- versioned fields ---------------------------------------
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  private VersionSource memberId;
  private short entryVersionLowBytes;
  private short regionVersionHighBytes;
  private int regionVersionLowBytes;
  private byte entryVersionHighByte;
  private byte distributedSystemId;
  // --------------------------------------- key fields -------------------------------------------
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  private final long bits1;

  public VersionedThinDiskLRURegionEntryOffHeapStringKey1(final RegionEntryContext context,
      final String key, @Retained final Object value, final boolean byteEncode) {
    super(context, (value instanceof RecoveredEntry ? null : value));
    // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
    initialize(context, value);
    // caller has already confirmed that key.length <= MAX_INLINE_STRING_KEY
    long tempBits1 = 0L;
    if (byteEncode) {
      for (int i = key.length() - 1; i >= 0; i--) {
        // Note: we know each byte is <= 0x7f so the "& 0xff" is not needed. But I added it in to
        // keep findbugs happy.
        tempBits1 |= (byte) key.charAt(i) & 0xff;
        tempBits1 <<= 8;
      }
      tempBits1 |= 1 << 6;
    } else {
      for (int i = key.length() - 1; i >= 0; i--) {
        tempBits1 |= key.charAt(i);
        tempBits1 <<= 16;
      }
    }
    tempBits1 |= key.length();
    bits1 = tempBits1;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  public Token getValueAsToken() {
    return OffHeapRegionEntryHelper.getValueAsToken(this);
  }

  @Override
  protected Object getValueField() {
    return OffHeapRegionEntryHelper._getValue(this);
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  @Unretained
  protected void setValueField(@Unretained final Object value) {
    OffHeapRegionEntryHelper.setValue(this, value);
  }

  @Override
  @Retained
  public Object getValueRetain(final RegionEntryContext context, final boolean decompress) {
    return OffHeapRegionEntryHelper._getValueRetain(this, decompress, context);
  }

  @Override
  public long getAddress() {
    return OFF_HEAP_ADDRESS_UPDATER.get(this);
  }

  @Override
  public boolean setAddress(final long expectedAddress, long newAddress) {
    return OFF_HEAP_ADDRESS_UPDATER.compareAndSet(this, expectedAddress, newAddress);
  }

  @Override
  @Released
  public void release() {
    OffHeapRegionEntryHelper.releaseEntry(this);
  }

  @Override
  public void returnToPool() {
    // never implemented
  }

  @Override
  protected long getLastModifiedField() {
    return LAST_MODIFIED_UPDATER.get(this);
  }

  @Override
  protected boolean compareAndSetLastModifiedField(final long expectedValue, final long newValue) {
    return LAST_MODIFIED_UPDATER.compareAndSet(this, expectedValue, newValue);
  }

  @Override
  public int getEntryHash() {
    return hash;
  }

  @Override
  protected void setEntryHash(final int hash) {
    this.hash = hash;
  }

  @Override
  public HashEntry<Object, Object> getNextEntry() {
    return nextEntry;
  }

  @Override
  public void setNextEntry(final HashEntry<Object, Object> nextEntry) {
    this.nextEntry = nextEntry;
  }

  // ----------------------------------------- disk code ------------------------------------------
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  protected void initialize(final RegionEntryContext context, final Object value) {
    boolean isBackup;
    if (context instanceof InternalRegion) {
      isBackup = ((InternalRegion) context).getDiskRegion().isBackup();
    } else if (context instanceof PlaceHolderDiskRegion) {
      isBackup = true;
    } else {
      throw new IllegalArgumentException("expected a InternalRegion or PlaceHolderDiskRegion");
    }
    // Delay the initialization of DiskID if overflow only
    if (isBackup) {
      diskInitialize(context, value);
    }
  }

  @Override
  public synchronized int updateAsyncEntrySize(final EvictionController evictionController) {
    int oldSize = getEntrySize();
    int newSize = evictionController.entrySize(getKeyForSizing(), null);
    setEntrySize(newSize);
    int delta = newSize - oldSize;
    return delta;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  public DiskId getDiskId() {
    return id;
  }

  @Override
  public void setDiskId(final RegionEntry oldEntry) {
    id = ((DiskEntry) oldEntry).getDiskId();
  }

  private void diskInitialize(final RegionEntryContext context, final Object value) {
    DiskRecoveryStore diskRecoveryStore = (DiskRecoveryStore) context;
    DiskStoreImpl diskStore = diskRecoveryStore.getDiskStore();
    long maxOplogSize = diskStore.getMaxOplogSize();
    // get appropriate instance of DiskId implementation based on maxOplogSize
    id = DiskId.createDiskId(maxOplogSize, true, diskStore.needsLinkedList());
    Helper.initialize(this, diskRecoveryStore, value);
  }

  // --------------------------------------- eviction code ----------------------------------------
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  public void setDelayedDiskId(final DiskRecoveryStore diskRecoveryStore) {
    DiskStoreImpl diskStore = diskRecoveryStore.getDiskStore();
    long maxOplogSize = diskStore.getMaxOplogSize();
    id = DiskId.createDiskId(maxOplogSize, false, diskStore.needsLinkedList());
  }

  @Override
  public synchronized int updateEntrySize(final EvictionController evictionController) {
    // OFFHEAP: getValue ok w/o incing refcount because we are synced and only getting the size
    return updateEntrySize(evictionController, getValue());
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  public synchronized int updateEntrySize(final EvictionController evictionController,
      final Object value) {
    int oldSize = getEntrySize();
    int newSize = evictionController.entrySize(getKeyForSizing(), value);
    setEntrySize(newSize);
    int delta = newSize - oldSize;
    return delta;
  }

  @Override
  public boolean isRecentlyUsed() {
    return areAnyBitsSet(RECENTLY_USED);
  }

  @Override
  public void setRecentlyUsed(RegionEntryContext context) {
    if (!isRecentlyUsed()) {
      setBits(RECENTLY_USED);
      context.incRecentlyUsed();
    }
  }

  @Override
  public void unsetRecentlyUsed() {
    clearBits(~RECENTLY_USED);
  }

  @Override
  public boolean isEvicted() {
    return areAnyBitsSet(EVICTED);
  }

  @Override
  public void setEvicted() {
    setBits(EVICTED);
  }

  @Override
  public void unsetEvicted() {
    clearBits(~EVICTED);
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  private EvictionNode nextEvictionNode;
  private EvictionNode previousEvictionNode;
  private int size;

  @Override
  public void setNext(final EvictionNode nextEvictionNode) {
    this.nextEvictionNode = nextEvictionNode;
  }

  @Override
  public EvictionNode next() {
    return nextEvictionNode;
  }

  @Override
  public void setPrevious(final EvictionNode previousEvictionNode) {
    this.previousEvictionNode = previousEvictionNode;
  }

  @Override
  public EvictionNode previous() {
    return previousEvictionNode;
  }

  @Override
  public int getEntrySize() {
    return size;
  }

  protected void setEntrySize(final int size) {
    this.size = size;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  public Object getKeyForSizing() {
    // inline keys always report null for sizing since the size comes from the entry size
    return null;
  }

  // -------------------------------------- versioned code ----------------------------------------
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  public int getEntryVersion() {
    return ((entryVersionHighByte << 16) & 0xFF0000) | (entryVersionLowBytes & 0xFFFF);
  }

  @Override
  public long getRegionVersion() {
    return (((long) regionVersionHighBytes) << 32) | (regionVersionLowBytes & 0x00000000FFFFFFFFL);
  }

  @Override
  public long getVersionTimeStamp() {
    return getLastModified();
  }

  @Override
  public void setVersionTimeStamp(final long timeStamp) {
    setLastModified(timeStamp);
  }

  @Override
  public VersionSource getMemberID() {
    return memberId;
  }

  @Override
  public int getDistributedSystemId() {
    return distributedSystemId;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  public void setVersions(final VersionTag versionTag) {
    memberId = versionTag.getMemberID();
    int eVersion = versionTag.getEntryVersion();
    entryVersionLowBytes = (short) (eVersion & 0xffff);
    entryVersionHighByte = (byte) ((eVersion & 0xff0000) >> 16);
    regionVersionHighBytes = versionTag.getRegionVersionHighBytes();
    regionVersionLowBytes = versionTag.getRegionVersionLowBytes();
    if (!versionTag.isGatewayTag()
        && distributedSystemId == versionTag.getDistributedSystemId()) {
      if (getVersionTimeStamp() <= versionTag.getVersionTimeStamp()) {
        setVersionTimeStamp(versionTag.getVersionTimeStamp());
      } else {
        versionTag.setVersionTimeStamp(getVersionTimeStamp());
      }
    } else {
      setVersionTimeStamp(versionTag.getVersionTimeStamp());
    }
    distributedSystemId = (byte) (versionTag.getDistributedSystemId() & 0xff);
  }

  @Override
  public void setMemberID(final VersionSource memberId) {
    this.memberId = memberId;
  }

  @Override
  public VersionStamp getVersionStamp() {
    return this;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  public VersionTag asVersionTag() {
    VersionTag tag = VersionTag.create(memberId);
    tag.setEntryVersion(getEntryVersion());
    tag.setRegionVersion(regionVersionHighBytes, regionVersionLowBytes);
    tag.setVersionTimeStamp(getVersionTimeStamp());
    tag.setDistributedSystemId(distributedSystemId);
    return tag;
  }

  @Override
  public void processVersionTag(final InternalRegion region, final VersionTag versionTag,
      final boolean isTombstoneFromGII, final boolean hasDelta, final VersionSource versionSource,
      final InternalDistributedMember sender, final boolean checkForConflicts) {
    basicProcessVersionTag(region, versionTag, isTombstoneFromGII, hasDelta, versionSource, sender,
        checkForConflicts);
  }

  @Override
  public void processVersionTag(final EntryEvent cacheEvent) {
    // this keeps IDE happy. without it the sender chain becomes confused while browsing this code
    super.processVersionTag(cacheEvent);
  }

  /** get rvv internal high byte. Used by region entries for transferring to storage */
  @Override
  public short getRegionVersionHighBytes() {
    return regionVersionHighBytes;
  }

  /** get rvv internal low bytes. Used by region entries for transferring to storage */
  @Override
  public int getRegionVersionLowBytes() {
    return regionVersionLowBytes;
  }

  // ----------------------------------------- key code -------------------------------------------
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  private int getKeyLength() {
    return (int) (bits1 & 0x003fL);
  }

  private int getEncoding() {
    // 0 means encoded as char
    // 1 means encoded as bytes that are all <= 0x7f;
    return (int) (bits1 >> 6) & 0x03;
  }

  @Override
  public Object getKey() {
    int keyLength = getKeyLength();
    char[] chars = new char[keyLength];
    long tempBits1 = bits1;
    if (getEncoding() == 1) {
      for (int i = 0; i < keyLength; i++) {
        tempBits1 >>= 8;
        chars[i] = (char) (tempBits1 & 0x00ff);
      }
    } else {
      for (int i = 0; i < keyLength; i++) {
        tempBits1 >>= 16;
        chars[i] = (char) (tempBits1 & 0x00FFff);
      }
    }
    return new String(chars);
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  public boolean isKeyEqual(final Object key) {
    if (key instanceof String) {
      String stringKey = (String) key;
      int keyLength = getKeyLength();
      if (stringKey.length() == keyLength) {
        long tempBits1 = bits1;
        if (getEncoding() == 1) {
          for (int i = 0; i < keyLength; i++) {
            tempBits1 >>= 8;
            char character = (char) (tempBits1 & 0x00ff);
            if (stringKey.charAt(i) != character) {
              return false;
            }
          }
        } else {
          for (int i = 0; i < keyLength; i++) {
            tempBits1 >>= 16;
            char character = (char) (tempBits1 & 0x00FFff);
            if (stringKey.charAt(i) != character) {
              return false;
            }
          }
        }
        return true;
      }
    }
    return false;
  }
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
}
